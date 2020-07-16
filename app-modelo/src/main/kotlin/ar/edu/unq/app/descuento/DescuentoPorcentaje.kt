package ar.edu.unq.app.descuento

class DescuentoPorcentaje(val descuento: Double): TipoDescuento(nombre = "Porcentaje"){

    override fun obtenerDescuento() : Double {
        return descuento
    }

    override fun aplicarDescuento(importe: Double): Double {
        val desc = importe * descuento / 100
        return importe - desc
    }
}