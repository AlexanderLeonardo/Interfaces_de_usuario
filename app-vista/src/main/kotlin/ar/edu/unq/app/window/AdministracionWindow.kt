package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.AdministracionAppModel
import ar.edu.unq.app.appModel.MenuAppModel
import ar.edu.unq.app.appModel.ProductoAppModel
import ar.edu.unq.app.dialogo.DialogoConfirmacionMenu
import ar.edu.unq.app.dialogo.DialogoConfirmacionProducto
import ar.edu.unq.app.wrapper.MenuWrapper
import ar.edu.unq.app.wrapper.ProductoWrapper
import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.arena.layout.ColumnLayout
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.ControlBuilder
import org.uqbar.lacar.ui.model.bindings.Binding

class AdministracionWindow(owner: WindowOwner, model: AdministracionAppModel) : SimpleWindow<AdministracionAppModel>(owner, model) {
    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "MorfiYa :: " + modelObject.restaurant?.nombre

        modelObject.completarListaDeProductos()
        modelObject.completarListaDeMenues()

        var columnPanel1 = Panel(mainPanel).setLayout(ColumnLayout(2))

        var verticalPanel1 = Panel(columnPanel1).setLayout(VerticalLayout())
        var verticalPanel2 = Panel(columnPanel1).setLayout(VerticalLayout())
        var columnPanel2 = Panel(columnPanel1).setLayout(ColumnLayout(2))
        var horizontalPanel3 = Panel(columnPanel1).setLayout(HorizontalLayout())

        Label(verticalPanel1)
            .setText("Administrar Productos")
            .setFontSize(20)

        Label(verticalPanel2)
            .setText("Administrar Menues")
            .setFontSize(20)

        var horizontalPanel1 = Panel(verticalPanel1).setLayout(HorizontalLayout())
        var horizontalPanel2 = Panel(verticalPanel2).setLayout(HorizontalLayout())

        TextBox(horizontalPanel1)
            .setWidth(200)
            .bindValueToProperty<ProductoWrapper, ControlBuilder>("filtroProducto")
        Button(horizontalPanel1)
            .setCaption("Buscar Producto")
            .onClick {
                modelObject.buscarProductosPorTexto()
            }

        TextBox(horizontalPanel2)
            .setWidth(200)
            .bindValueToProperty<MenuWrapper, ControlBuilder>("filtroMenu")
        Button(horizontalPanel2)
            .setCaption("Buscar Menu")
            .onClick {
                modelObject.buscarMenuesPorTexto()
            }


        var tablaProductos = Table<ProductoWrapper>(verticalPanel1, ProductoWrapper::class.java)
        tablaProductos.bindItemsToProperty("listaDeProductos")
        tablaProductos.bindValueToProperty<ProductoWrapper, ControlBuilder>("productoSeleccionado")

        Column<ProductoWrapper>(tablaProductos)
            .setTitle("Nombre")
            .setFixedSize(150)
            .bindContentsToProperty("nombre")
        Column<ProductoWrapper>(tablaProductos)
            .setTitle("Precio")
            .setFixedSize(100)
            .bindContentsToProperty("convertirPrecio")

        var tablaMenues = Table<MenuWrapper>(verticalPanel2, MenuWrapper::class.java)
        tablaMenues.bindItemsToProperty("listaDeMenues")
        tablaMenues.bindValueToProperty<MenuWrapper,ControlBuilder>("menuSeleccionado")

        Column<MenuWrapper>(tablaMenues)
            .setTitle("Menú")
            .setFixedSize(125)
            .bindContentsToProperty("nombre")
        Column<MenuWrapper>(tablaMenues)
            .setTitle("Precio")
            .setFixedSize(60)
            .bindContentsToProperty("calcularCostoMenu")
        Column<MenuWrapper>(tablaMenues)
            .setTitle("Estado")
            .setFixedSize(75)
            .bindContentsToProperty("convertirEstado")

        val editButton = Button(columnPanel2).setCaption("Modificar")

        editButton.onClick({
            if(modelObject.productoSeleccionado != null) {
            var productoAppModel = ProductoAppModel(modelObject.productoSeleccionado!!,modelObject.restaurant?.nombre,modelObject.morfiYa,modelObject)
            val verProductoWindow = ProductoWindow(this,productoAppModel)
            verProductoWindow.accionVentana = "Modificar Producto"

                verProductoWindow.open()
            }
        })
            .setWidth(156)

        Button(columnPanel2)
            .setCaption("Agregar")
            .onClick({
                var productoAppModel = ProductoAppModel(modelObject.productoNuevo,modelObject.restaurant?.nombre,modelObject.morfiYa,modelObject)
                val verProductoWindow = ProductoWindow(this,productoAppModel)
                verProductoWindow.accionVentana = "Agregar Producto"
                verProductoWindow.open()
            })
            .setWidth(156)

        var verMenuButton = Button(columnPanel2)
            .setCaption("Ver Menues")
            .onClick({
                if(modelObject.productoSeleccionado != null) {
                var productoAppModel = ProductoAppModel(modelObject.productoSeleccionado!!,modelObject.restaurant?.nombre,modelObject.morfiYa,modelObject)
                val verMenuesProductoWindow = MenuesContienenProductoWindow(this,productoAppModel)
                    verMenuesProductoWindow.open()
                }
            })

            .setWidth(156)

        var botonEliminar = Button(columnPanel2)
            .setCaption("Eliminar")
            .onClick({
                if(modelObject.productoSeleccionado != null) {
                var productoAppModel = ProductoAppModel(modelObject.productoSeleccionado!!,modelObject.restaurant?.nombre,modelObject.morfiYa,modelObject)
                val dialogoConfirmacion = DialogoConfirmacionProducto(this,productoAppModel)
                dialogoConfirmacion.mensaje = "Esta seguro que desea eliminar el producto "
                    dialogoConfirmacion.open()
                }
            })
            .setWidth(156)

        var verMenu = Button(horizontalPanel3)
            .setCaption("Ver")
            .onClick({
                var menuAppModel = MenuAppModel(modelObject.menuSeleccionado,modelObject.restaurant,modelObject.morfiYa,modelObject)
                var vistaMenuWindow = VistaMenuWindow(this,menuAppModel)
                if(modelObject.menuSeleccionado != null) {
                    vistaMenuWindow.open()
                }
            })
            .setWidth(73)

        var agregarMenu = Button(horizontalPanel3)
            .setCaption("Agregar")
            .onClick({
                var menuAppModel = MenuAppModel(modelObject.menuNuevo,modelObject.restaurant, modelObject.morfiYa,modelObject)
                var menuWindow = MenuWindow(this,menuAppModel)
                menuWindow.accionVentana = "Nuevo Menú"
                menuWindow.open()
            })
            .setWidth(73)

        var modificarMenu = Button(horizontalPanel3)
            .setCaption("Modificar")
            .onClick({
                var menuAppModel = MenuAppModel(modelObject.menuSeleccionado,modelObject.restaurant, modelObject.morfiYa,modelObject)
                var menuWindow = MenuWindow(this,menuAppModel)
                menuWindow.accionVentana = "Modificar Menú"
                if(modelObject.menuSeleccionado != null) {
                    menuWindow.open()
                }
            })
            .setWidth(73)

        var eliminarMenu = Button(horizontalPanel3)
            .setCaption("Eliminar")
            .onClick({
                var menuAppModel = MenuAppModel(modelObject.menuSeleccionado,modelObject.restaurant, modelObject.morfiYa,modelObject)
                val dialogoConfirmacion = DialogoConfirmacionMenu(this,menuAppModel)
                dialogoConfirmacion.mensaje ="Esta seguro que desea eliminar el menu "
                if(modelObject.menuSeleccionado != null) {
                    dialogoConfirmacion.open()
                }
            })

            .setWidth(73)



    }
}