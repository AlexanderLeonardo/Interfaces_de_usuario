package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.ProductoAppModel
import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.ControlBuilder

class ProductoWindow(owner: WindowOwner, model: ProductoAppModel) : SimpleWindow<ProductoAppModel>(owner, model) {

    var accionVentana: String? = null

    override fun addActions(p0: Panel?) {}


    override fun createFormPanel(mainPanel: Panel) {
        title = "${modelObject.nombreRestaurant} :: $accionVentana"

        var labelPrincipal = Label(mainPanel)

        var horizontalPanel1 = Panel(mainPanel).setLayout(HorizontalLayout())
        var horizontalPanel2 = Panel(mainPanel).setLayout(HorizontalLayout())
        var horizontalPanel3 = Panel(mainPanel).setLayout(HorizontalLayout())
        var horizontalPanel4 = Panel(mainPanel).setLayout(HorizontalLayout())
        var horizontalPanel5 = Panel(mainPanel).setLayout(HorizontalLayout())

        Label(horizontalPanel1)
            .setText("código")
            .setWidth(50)
        var codigoInput = TextBox(horizontalPanel1)
            .setWidth(225)

        codigoInput.bindEnabled<Any,ControlBuilder>(ObservableProperty(modelObject,"enabled"))

        Label(horizontalPanel2)
            .setText("nombre")
            .setWidth(50)
        var nombreInput = TextBox(horizontalPanel2)
            .setWidth(225)


        Label(horizontalPanel3)
            .setText("descrip")
            .setWidth(50)
        var descripInput = TextBox(horizontalPanel3)
            .setWidth(225)

        Label(horizontalPanel4)
            .setText("precio $")
            .setWidth(50)
        var precioInput = TextBox(horizontalPanel4)
            .setWidth(80)

        Label(horizontalPanel4)
            .setText("categor.")

        var radioSelector = RadioSelector<String>(horizontalPanel4)
        radioSelector.bindItems(ObservableProperty<String>(modelObject, "listaCategorias"))
        radioSelector.setWidth(100)

        Label(horizontalPanel5)
            .setWidth(50)

        Button(horizontalPanel5)
            .setCaption("Cancelar")
            .onClick { close() }
            .setWidth(117)

        var botonAceptar = Button(horizontalPanel5)
        botonAceptar.setCaption("Aceptar")
        botonAceptar.setWidth(117)

        if (modelObject.producto?.codigo == null) {

            labelPrincipal
                .setText("NuevoProducto")
                .setFontSize(20)
                .alignLeft()

            codigoInput.bindValueToProperty<String, ControlBuilder>("codigoProductoAGuardar")
            nombreInput.bindValueToProperty<String, ControlBuilder>("nombreProductoAGuardar")
            descripInput.bindValueToProperty<String, ControlBuilder>("descripcionProductoAGuardar")
            precioInput.bindValueToProperty<Double, ControlBuilder>("precioProductoAGuardar")
            radioSelector.bindValueToProperty<String, ControlBuilder>("categoriaProductoAGuardar")

            botonAceptar.onClick {
                modelObject.guardarProducto()
                close()
            }
        } else {

            labelPrincipal
                .setText("Modificar ${modelObject.producto?.nombre}")
                .setFontSize(20)
                .alignLeft()

            codigoInput.bindValueToProperty<String, ControlBuilder>("codigoProductoSeleccionado")
            nombreInput.bindValueToProperty<String, ControlBuilder>("nombreProductoSeleccionado")
            descripInput.bindValueToProperty<String, ControlBuilder>("descripcionProductoSeleccionado")
            precioInput.bindValueToProperty<Double, ControlBuilder>("precioProductoSeleccionado")
            radioSelector.bindValueToProperty<String, ControlBuilder>("categoriaProductoSeleccionado")

            botonAceptar.onClick {
                modelObject.modificarProducto()
                close()
            }
        }

    }
}