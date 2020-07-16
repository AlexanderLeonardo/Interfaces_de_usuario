package ar.edu.unq.app.mediosDePago

data class Efectivo(var metodoDePago: String = "Efectivo") : MedioDePago(metodoDePago)