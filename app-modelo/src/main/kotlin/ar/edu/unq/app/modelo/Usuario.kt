package ar.edu.unq.app.modelo

import ar.edu.unq.app.mediosDePago.MedioDePago
import exceptions.DistanciaExcedidaParaEntregaDePedidoException
import exceptions.MedioDePagoNoAceptadoPorElRestaurantException
import exceptions.MenuesDeDistintosRestaurantesException
import exceptions.MenuesVaciosException
import java.time.LocalDate

class Usuario(var nombre: String, var email: String, var contrasena: String, var fechaDeRegistro: LocalDate, var direccion: String, var coordenadas: Geo,
              var pedidosRealizados: MutableList<Pedido>) {

    fun realizarPedido(pedido: Pedido) {
        validarPedido(pedido)
        pedidosRealizados.add(pedido)
    }

    private fun validarPedido(pedido: Pedido) {
        validarMenuesPertenecientesAlRestaurant(pedido.restaurant, pedido.menues);
        validarMenuNoVacio(pedido.menues)
        validarMedioDePagoPermitidoPorElRestaurant(pedido.restaurant, pedido.medioDePago)
        validarDistanciaHastaRestauranteNoSupera20Kms(pedido.restaurant)
    }

    private fun validarMenuesPertenecientesAlRestaurant(restaurant: Restaurant, menues: Map<Menu, Int>) {
        var menuesRestaurant = restaurant.menues
        if (!menuesRestaurant.containsAll(menues.keys)) {
            throw MenuesDeDistintosRestaurantesException("Los menues pedidos son de distintos restaurantes")
        }
    }

    private fun validarMenuNoVacio(menues: Map<Menu, Int>) {
        if (menues.isEmpty()) {
            throw MenuesVaciosException("No existen menues en el pedido")
        }
    }

    private fun validarMedioDePagoPermitidoPorElRestaurant(restaurant: Restaurant, medioDePago: MedioDePago) {
        var res = false
        for(metodoDePago: MedioDePago in restaurant.mediosDePago)
            res = res || medioDePago::class == metodoDePago::class
        if (!res) {
            throw MedioDePagoNoAceptadoPorElRestaurantException("El medio de pago no es permitido por el restaurant")
        }
    }

    private fun validarDistanciaHastaRestauranteNoSupera20Kms(restaurant: Restaurant) {
        var coordenadasRestaurant = restaurant.coordenadas
        if (GeoCalculator.distance(coordenadasRestaurant, this.coordenadas) >= 20.0) {
            throw DistanciaExcedidaParaEntregaDePedidoException("El restaurant se encuentra a mas de 20 kms del domicilio del usuario")
        }
    }
}