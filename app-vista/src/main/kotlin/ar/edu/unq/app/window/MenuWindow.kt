package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.MenuAppModel
import ar.edu.unq.app.wrapper.ProductoWrapper
import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.ControlBuilder

class MenuWindow(owner: WindowOwner, model: MenuAppModel) : SimpleWindow<MenuAppModel>(owner, model) {
    var accionVentana = ""

    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        modelObject.inicializaListasDeProductosWrapper()

        title = "${modelObject.restaurant?.nombre} :: $accionVentana"

        var labelPrincipal = Label(mainPanel)


        var horizontalLayout1 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout2 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout3 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout4 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout5 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout6 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout7 = Panel(mainPanel)
            .setLayout(HorizontalLayout())

        var horizontalLayout8 = Panel(mainPanel)
            .setLayout(HorizontalLayout())


        Label(horizontalLayout1)
            .setText("codigo")
            .setWidth(50)
        var codigoInput = TextBox(horizontalLayout1)
            .setWidth(250)

        codigoInput.bindEnabled<Any,ControlBuilder>(ObservableProperty(modelObject,"enabled"))

        Label(horizontalLayout2)
            .setText("nombre")
            .setWidth(50)
        var nombreInput = TextBox(horizontalLayout2)
            .setWidth(250)

        Label(horizontalLayout3)
            .setText("descrip")
            .setWidth(50)
        var descripInput = TextBox(horizontalLayout3)
            .setWidth(250)

        Label(horizontalLayout4)
            .setText("TipoDesc")
            .setWidth(50)

        var radioSelector = RadioSelector<String>(horizontalLayout4)
        radioSelector.bindItems(ObservableProperty<String>(modelObject, "listaTiposDeDescuento"))
        radioSelector.bindValueToProperty<String, ControlBuilder>("tipoDescuento")
        radioSelector.setWidth(100)
        Label(horizontalLayout4)
            .setText("Descuen")
            .setWidth(60)
        var descuenInput = TextBox(horizontalLayout4)
            .setWidth(83)

        descuenInput.bindValueToProperty<Double,ControlBuilder>("valorDescuento")

        Label(horizontalLayout5)
            .setText("Disponible")
            .setFontSize(14)
            .alignLeft()

        Label(horizontalLayout5)
            .setWidth(70)

        Label(horizontalLayout5)
            .setText("Seleccionado")
            .setFontSize(14)
            .alignRight()

        var tablaProductosDisponibles = Table<ProductoWrapper>(horizontalLayout6,ProductoWrapper::class.java)

        tablaProductosDisponibles.bindItemsToProperty("listaDeProductosRestaurantModificable")
        tablaProductosDisponibles.bindValueToProperty<ProductoWrapper,ControlBuilder>("productoSeleccionadoRestaurant")
        tablaProductosDisponibles.setWidth(110)
        tablaProductosDisponibles.setHeight(150)

        Column<ProductoWrapper>(tablaProductosDisponibles)
            .setTitle("Producto disponible")
            .setFixedSize(105)
            .bindContentsToProperty("nombre")

        var verticalLayout = Panel(horizontalLayout6)
            .setLayout(VerticalLayout())

        var tablaProductosMenu = Table<ProductoWrapper>(horizontalLayout6, ProductoWrapper::class.java)

        tablaProductosMenu.bindItemsToProperty("listaDeProductosMenuModificable")
        tablaProductosMenu.bindValueToProperty<String,ControlBuilder>("productoSeleccionadoMenu")
        tablaProductosMenu.setWidth(110)
        tablaProductosMenu.setHeight(150)

        Column<ProductoWrapper>(tablaProductosMenu)
            .setTitle("Producto en Menu")
            .setFixedSize(115)
            .bindContentsToProperty("nombre")

        var botonAgregar = Button(verticalLayout)
            .onClick({
                if(modelObject.productoSeleccionadoRestaurant != null) {
                    modelObject.agregarProductoRestaurantAMenu()
                }
            })
            .setCaption(">>")

        var botonQuitar = Button(verticalLayout)
            .onClick({
                if(modelObject.productoSeleccionadoMenu != null) {
                    modelObject.quitarProductoDelMenu()
                }
            })
            .setCaption("<<")

        CheckBox(horizontalLayout7)
            .bindValueToProperty<Boolean, ControlBuilder>("estadoMenu")

        Label(horizontalLayout7)
            .setText("Habilitado")

        Label(horizontalLayout8)
            .setWidth(50)

        Button(horizontalLayout8)
            .setCaption("Cancelar")
            .onClick { close() }
            .setWidth(117)

        var botonAceptar = Button(horizontalLayout8)
            botonAceptar.setCaption("Aceptar")
            botonAceptar.setWidth(117)

        if(modelObject.menuSeleccionado?.codigo == null) {

            labelPrincipal
                .setText(accionVentana)
                .setFontSize(20)
                .alignLeft()

            botonAceptar.onClick {
                modelObject.agregarNuevoMenu()
                close()
            }

            codigoInput.bindValueToProperty<String, ControlBuilder>("codigoMenuNuevo")
            nombreInput.bindValueToProperty<String, ControlBuilder>("nombreMenuNuevo")
            descripInput.bindValueToProperty<String, ControlBuilder>("descripcionMenuNuevo")
            descuenInput.bindValueToProperty<Double, ControlBuilder>("descuentoMenuNuevo")

        }else {

            labelPrincipal
                .setText("Modificar ${modelObject.menuSeleccionado?.nombre}")
                .setFontSize(20)
                .alignLeft()

            botonAceptar.onClick {
                modelObject.modificarMenu()
                close()
            }

            codigoInput.bindValueToProperty<String, ControlBuilder>("codigoMenuSeleccionado")
            nombreInput.bindValueToProperty<String, ControlBuilder>("nombreMenuSeleccionado")
            descripInput.bindValueToProperty<String, ControlBuilder>("descripcionMenuSeleccionado")
            descuenInput.bindValueToProperty<Double, ControlBuilder>("descuentoMenuSeleccionado")
        }
    }
}