package ar.edu.unq.app.descuento

import ar.edu.unq.app.descuento.TipoDescuento

class SinDescuento() : TipoDescuento(nombre = "Sin Descuento") {



    override fun obtenerDescuento() : Double {
        return 0.0
    }

    override fun aplicarDescuento(importe: Double): Double {
        // No hace nada (retorna el importe sin aplicarle ningun descuento)
        return importe
    }
}