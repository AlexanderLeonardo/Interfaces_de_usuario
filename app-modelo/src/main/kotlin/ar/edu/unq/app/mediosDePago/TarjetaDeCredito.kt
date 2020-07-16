package ar.edu.unq.app.mediosDePago

data class TarjetaDeCredito(var metodoDePago: String = "TarjetaDeCredito", var nombre: String, var numero: String, var codigoSeguridad: String, var tipo: String, var vencimiento: String) : MedioDePago(metodoDePago)