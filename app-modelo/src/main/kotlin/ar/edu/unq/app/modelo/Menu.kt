package ar.edu.unq.app.modelo

import ar.edu.unq.app.descuento.TipoDescuento

class Menu(val codigo: String, var nombre: String, var descripcion: String, var productos: Map<Producto,Int>, var descuento: TipoDescuento, var estado: Boolean) {

    fun calcularCostoMenu(): Double {
        var resultadoConDescuento : Double = 0.0
        var resultadoSinDescuento = calcularCostoMenuSinDescuento()
        resultadoConDescuento = descuento.aplicarDescuento(resultadoSinDescuento)
        return resultadoConDescuento
    }

    fun calcularCostoMenuSinDescuento() : Double {
        var res: Double = 0.0
        for (producto in productos) {
            res += producto.key.precio * producto.value
        }
        return res
    }
}