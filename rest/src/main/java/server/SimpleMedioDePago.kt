package server

abstract class SimpleMedioDePago(var metodoDePago: String)


class SimpleEfectivo(var medioDePago: String = "Efectivo") : SimpleMedioDePago(medioDePago)

data class SimpleTarjetaDeCredito(var name: String, var number: String, var securityCode: String, var type: String, var vencimiento: String)

class SimpleMercadoPago(var medioDePago: String = "MercadoPago"): SimpleMedioDePago(medioDePago)