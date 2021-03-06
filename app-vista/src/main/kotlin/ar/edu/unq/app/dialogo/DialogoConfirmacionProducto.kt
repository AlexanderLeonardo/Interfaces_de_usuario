package ar.edu.unq.app.dialogo

import ar.edu.unq.app.appModel.ProductoAppModel
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner


class DialogoConfirmacionProducto(owner: WindowOwner, model: ProductoAppModel) : Dialog<ProductoAppModel>(owner, model) {
    var mensaje = ""

    override fun createFormPanel(mainPanel: Panel) {
        Label(mainPanel)
            .setText(mensaje + modelObject.producto?.nombre)
            .setFontSize(12)

        var horizontalPanel = Panel(mainPanel).setLayout(HorizontalLayout())

        Label(horizontalPanel).setWidth(250)

        Button(horizontalPanel)
            .setCaption("Cancelar")
            .onClick { cancel() }
            .setWidth(100)
            .setHeight(30)

        Button(horizontalPanel)
            .setCaption("Aceptar")
            .onClick {
                modelObject.eliminarProducto()
                accept()
            }
            .setWidth(100)
            .setHeight(30)
    }
}