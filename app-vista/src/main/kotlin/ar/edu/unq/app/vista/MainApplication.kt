package ar.edu.unq.app.vista

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.appModel.LoginAppModel
import ar.edu.unq.app.window.LoginWindow
import org.uqbar.arena.Application

fun main() = MainApplication().start()

class MainApplication : Application() {
    override fun createMainWindow(): LoginWindow {
        val modeloLogin = LoginAppModel(MorfiYa())

        return LoginWindow(this, modeloLogin)
    }
}