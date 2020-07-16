package ar.edu.unq.app.window

import ar.edu.unq.app.appModel.AdministracionAppModel
import ar.edu.unq.app.appModel.LoginAppModel
import org.uqbar.arena.layout.ColumnLayout
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.ControlBuilder
import java.awt.Color

class LoginWindow(owner: WindowOwner, model: LoginAppModel) : SimpleWindow<LoginAppModel>(owner, model) {
    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "Login"

        Label(mainPanel).setText("Bienvenidos a MorfiYa")
            .setFontSize(25)

        var horizontalPanel1 = Panel(mainPanel).setLayout(HorizontalLayout())
        var horizontalPanel2 = Panel(mainPanel).setLayout(HorizontalLayout())
        var columnPanel = Panel(mainPanel).setLayout(ColumnLayout(2))

        Label(horizontalPanel1)
            .setText("Usuario:")
            .setWidth(80)
        var username = TextBox(horizontalPanel1)
            .setWidth(180)
        Label(horizontalPanel2)
            .setText("Contraseña:")
            .setWidth(80)
        var password = PasswordField(horizontalPanel2)
            .setWidth(180)
        Label(columnPanel)
        var ingresar = Button(columnPanel)
            .setCaption("Ingresar")

        username.bindValueToProperty<String, ControlBuilder>("nombreUsuario")
        password.bindValueToProperty<String, ControlBuilder>("contrasena")


        ingresar.onClick{
            if(modelObject.validarUsuarioSupervisor() != null) {
                var modeloAdministracion = AdministracionAppModel(modelObject.morfiYa)
                modeloAdministracion.restaurant = modelObject.restaurant
                val adminWindow = AdministracionWindow(this, modeloAdministracion)
                this.close()
                adminWindow.open()
            }
        }.setWidth(80)

    }
}