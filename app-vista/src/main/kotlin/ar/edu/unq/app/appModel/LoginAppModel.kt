package ar.edu.unq.app.appModel

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.excepciones.UsuarioException
import ar.edu.unq.app.modelo.Restaurant
import org.uqbar.commons.model.annotations.Observable

@Observable
class LoginAppModel (var morfiYa: MorfiYa){

    var nombreUsuario : String = ""
    var contrasena : String = ""

    var restaurant : Restaurant? = null

    fun validarUsuarioSupervisor(): Restaurant? {
        restaurant = morfiYa.validarUsuarioSupervisor(nombreUsuario,contrasena)
        verificarUsuarioLogueado(restaurant)
        return restaurant
    }

    fun verificarUsuarioLogueado(restaurant: Restaurant?) {
        if(restaurant == null) {
            throw UsuarioException("El usuario o la contraseña son incorrectas")
        }
    }
}