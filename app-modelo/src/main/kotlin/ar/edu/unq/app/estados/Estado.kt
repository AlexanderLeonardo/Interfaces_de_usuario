package estados

abstract class Estado(var state: String) {


    abstract fun cambiarDeEstado()
}