package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.ProductoAppModel
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class MenuesContienenProductoWindow(owner: WindowOwner, model: ProductoAppModel) : SimpleWindow<ProductoAppModel>(owner, model) {
    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "Menues de Producto"

        Label(mainPanel)
            .setText("Menues que tienen ${modelObject.producto?.nombre}")
            .setFontSize(20)
            .alignLeft()

        for(menu in modelObject.listaDeMenuesDeProductoSeleccionado) {
            Label(mainPanel)
                .setText("* Menu ${menu.nombre} ")
                .setFontSize(15)
                .alignLeft()
        }

        if(modelObject.listaDeMenuesDeProductoSeleccionado.isEmpty()){
            Label(mainPanel)
                .setText("* El producto no esta asociado a ningun menú")
                .setFontSize(15)
                .alignLeft()
        }

        var horizontalPanel = Panel(mainPanel).setLayout(HorizontalLayout())

        Label(horizontalPanel)
            .setWidth(250)


        Button(horizontalPanel)
            .setCaption("Cerrar")
            .onClick { close() }
            .setFontSize(14)
            .setWidth(80)
    }
}