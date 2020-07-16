package ar.edu.unq.app.wrapper

import ar.edu.unq.app.modelo.Producto
import org.uqbar.commons.model.annotations.Observable

@Observable
class ProductoWrapper {

    var producto : Producto? = null

    var nombre : String? = null
    var precio : Double? = null
    var codigo : String? = null
    var descripcion : String? = null
    var categoria : String? = null

    fun convertirPrecio(): String {
        if(precio != null) {
            return "$"+ precio
        } else {
            return "Precio No Disponible"
        }
    }
}