package server


    data class SimplePedido(var id: Int, var restaurantId: Int, var nombreRestaurant: String, var menus: MutableList<SimpleMenu>, var estado: String,
                            var costoTotalPedido: Double, var tarjetaDeCredito: SimpleTarjetaDeCredito?, var destination: SimpleGeo,
                            var efectivo: Boolean?, var mercadoPago: SimpleMercadoPago?)