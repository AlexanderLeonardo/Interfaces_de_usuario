package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.MenuAppModel
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class VistaMenuWindow(owner: WindowOwner, model: MenuAppModel) : SimpleWindow<MenuAppModel>(owner, model) {
    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "Productos de Menu"

        Label(mainPanel)
            .setText("Productos del menu ${modelObject.menuSeleccionado?.nombre}")
            .setFontSize(18)
            .alignLeft()

        for((producto,cantidad) in modelObject.productosDeMenuSeleccionado) {

            Label(mainPanel)
                .setText("* ${producto.nombre} x ${cantidad} = $${producto.precio * cantidad}")
                .setFontSize(15)
                .alignLeft()
        }

        Label(mainPanel)
            .setText("TOTAL:${modelObject.menuSeleccionado?.calcularCostoMenuSinDescuento()} - $${modelObject.menuSeleccionado?.descuento?.obtenerDescuento()}")
            .setFontSize(16)
            .alignLeft()

        var horizontalPanel = Panel(mainPanel).setLayout(HorizontalLayout())

        Label(horizontalPanel)
            .setWidth(280)

        Button(horizontalPanel)
            .setCaption("Cerrar")
            .onClick{close()}
            .setFontSize(14)
            .setWidth(100)

    }

}