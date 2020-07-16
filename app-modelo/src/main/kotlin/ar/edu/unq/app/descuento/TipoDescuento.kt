package ar.edu.unq.app.descuento

abstract class TipoDescuento(val nombre: String) {
    abstract fun aplicarDescuento(importe: Double): Double
    abstract fun obtenerDescuento() : Double
}