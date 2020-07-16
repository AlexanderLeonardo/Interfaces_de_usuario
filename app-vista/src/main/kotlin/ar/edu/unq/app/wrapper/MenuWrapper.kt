package ar.edu.unq.app.wrapper

import ar.edu.unq.app.descuento.TipoDescuento
import ar.edu.unq.app.modelo.Menu
import ar.edu.unq.app.modelo.Producto
import org.uqbar.commons.model.annotations.Observable

@Observable
class MenuWrapper {

    var menu: Menu? = null

    var nombre: String? = null
    var codigo: String? = null
    var descripcion: String? = null
    var descuento: TipoDescuento? = null
    var estado: Boolean = false

    var productos: Map<Producto, Int>? = null

    fun calcularCostoMenuSinDescuento(): String {
        if(menu?.calcularCostoMenuSinDescuento() != null) {
            return "$"+ menu?.calcularCostoMenuSinDescuento()
        } else {
            return "Precio No Disponible"
        }
    }

    fun calcularCostoMenu(): String {
        if(menu?.calcularCostoMenu() != null) {
            return "$"+ menu?.calcularCostoMenu()
        } else {
            return "Precio No Disponible"
        }
    }

    fun convertirEstado(): String {
        if(estado) {
            return "Disponible"
        } else {
            return "No Disponible"
        }
    }

}