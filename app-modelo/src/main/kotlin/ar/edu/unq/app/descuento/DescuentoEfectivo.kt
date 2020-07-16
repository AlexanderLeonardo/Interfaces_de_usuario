package ar.edu.unq.app.descuento

class DescuentoEfectivo(val descuento: Double): TipoDescuento(nombre = "Efectivo"){

    override fun obtenerDescuento() : Double {
        return descuento
    }

    override fun aplicarDescuento(importe: Double): Double {
        return importe - descuento
    }
}