package ar.edu.unq.app.modelo

import ar.edu.unq.app.mediosDePago.MedioDePago
import estados.Estado
import java.time.LocalDate

class Pedido(var codigo: String, var fecha: LocalDate, var restaurant: Restaurant, var menues: Map<Menu, Int>,
             var estado: Estado, var medioDePago: MedioDePago, var destino: Geo) {

    fun calcularPrecioTotalPedido(): Double {
        var total = 0.0
        for((menu, cantidad) in  menues) {
            total+= menu.calcularCostoMenu() * cantidad
        }
        return total
    }
}