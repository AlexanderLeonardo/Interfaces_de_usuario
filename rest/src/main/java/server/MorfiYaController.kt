package server

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.mediosDePago.MedioDePago
import ar.edu.unq.app.mediosDePago.TarjetaDeCredito
import ar.edu.unq.app.modelo.*
import estados.Estado
import io.javalin.NotFoundResponse
import java.time.LocalDate


data class UsuarioApi(val id: Int, var username: String, var email: String, var password: String, var address: String, var coordenadas: Geo, var pedidos: MutableList<SimplePedido>)

data class UsuarioLogin(var username: String, var password: String)

data class UsuarioView(var username: String, var email: String, var address: String, var coordenadas: SimpleGeo)

data class UsuarioRegister(var username: String, var email: String, var password: String, var address: String)

data class UsuarioViewOrder(var username: String, var email: String, var address: String, var coord: Geo, var pedidos: MutableList<SimplePedido>)

data class User(var username: String, var email: String, var password: String, var address: String, var coordenadas: Geo)



class MorfiYaController(val morfiYa: MorfiYa) {

    private var lastId = 0
    private var lastIdOrder = 0
    private var lastIdRestaurant = 0
    private var lastIdMenu = 0
    private var users = this.modelToApi()
    private var restaurants = this.restaurantToRestaurantAPI()
    private var orders = mutableListOf<SimplePedido>()


    fun estadoToSimpleEstado(estado: Estado): String{
        return estado.state
    }

    /*fun medioDePagoToSimpleMedioDePago(medioDePago: MedioDePago): SimpleMedioDePago{
        var res : SimpleMedioDePago = SimpleEfectivo()
        if(medioDePago::class == TarjetaDeCredito::class) {
            val credito = medioDePago as TarjetaDeCredito
            res = SimpleTarjetaDeCredito(credito.nombre, credito.numero, credito.codigoSeguridad, credito.tipo, credito.vencimiento)
        }
        if(medioDePago::class == MercadoPago::class){
            val mercadoPago = medioDePago as MercadoPago
            res = SimpleMercadoPago()
        }
        return res
    } */

    fun medioTarjetaDeCredito(medioDePago: MedioDePago): SimpleTarjetaDeCredito? {
        if(medioDePago::class == TarjetaDeCredito::class) {
            val credito = medioDePago as TarjetaDeCredito
            return SimpleTarjetaDeCredito(credito.nombre, credito.numero, credito.codigoSeguridad, credito.tipo, credito.vencimiento)
        }
        return null
    }

    fun medioEfectivo(medioDePago: MedioDePago): Boolean? {
        return null
    }

    fun medioMercadoPago(medioDePago: MedioDePago): SimpleMercadoPago? {
        return null
    }


    fun geoToSimpleGeo(geo: Geo): SimpleGeo {
        var res = SimpleGeo(geo.latitude, geo.longitude)
        return res
    }


    fun productsToProductosRestaurant(products: Map<Producto, Int>): MutableList<ProductRestaurant>{

        var res = mutableListOf<ProductRestaurant>()
            products.forEach { k, v -> res.add(ProductRestaurant(k.nombre, v))}
        return res
    }


    fun menusToSimpleMenus(menus: Map<Menu, Int>): MutableList<SimpleMenu>{  // pasarlo a lista?

        var res = mutableListOf<SimpleMenu>()
        menus.forEach { k, v -> res.add(SimpleMenu(k.nombre,k.calcularCostoMenu(),v)) }
        return res
    }

    fun menusToMenusRestaurant(menus: MutableList<Menu>): MutableList<MenuRestaurant>{

        var menusResto = mutableListOf<MenuRestaurant>()
        for(menu: Menu in menus)
            menusResto.add(MenuRestaurant(menu.codigo, menu.nombre, menu.calcularCostoMenu(), productsToProductosRestaurant(menu.productos)))
        return menusResto
    }


    fun pedidoToSimplePedido(user: Usuario): MutableList<SimplePedido> {
        var pedidos = mutableListOf<SimplePedido>()
        for(order: Pedido in user.pedidosRealizados)
            pedidos.add(SimplePedido(++lastIdOrder, order.restaurant.codigo.toInt(), order.restaurant.nombre, menusToSimpleMenus(order.menues), estadoToSimpleEstado(order.estado), order.calcularPrecioTotalPedido(),medioTarjetaDeCredito(order.medioDePago), geoToSimpleGeo(order.destino), medioEfectivo(order.medioDePago), medioMercadoPago(order.medioDePago)))
        return pedidos
    }

    fun modelToApi(): MutableList<UsuarioApi> {
        var resultado = mutableListOf<UsuarioApi>()
        for(user: Usuario in morfiYa.usuarios)
            resultado.add(UsuarioApi(++lastId, user.nombre, user.email, user.contrasena, user.direccion, user.coordenadas, pedidoToSimplePedido(user)))
        return resultado
    }

    fun getAllUsers(): List<UsuarioApi> = users


    fun addUser(usuario: Usuario): UsuarioView{
        val user = UsuarioApi(++lastId, usuario.nombre, usuario.email, usuario.contrasena, usuario.direccion, usuario.coordenadas, pedidoToSimplePedido(usuario))
        val userView = UsuarioView(usuario.nombre, usuario.email, usuario.direccion, SimpleGeo(usuario.coordenadas.latitude,usuario.coordenadas.longitude))
        users.add(user)
        return userView
    }

    fun addUserApi(usuario: UsuarioRegister): UsuarioView{
        val user = UsuarioApi(++lastId, usuario.username, usuario.email, usuario.password, usuario.address, Geo(150.0,-150.0,"Avellaneda"), mutableListOf<SimplePedido>())
        val userView = UsuarioView(usuario.username, usuario.email, usuario.address, SimpleGeo(150.0,-150.0))
        users.add(user)
        return userView
    }

    fun getUser(id: Int): UsuarioView {

        val user = users.firstOrNull { it.id == id }
        var res = UsuarioView("anName", "anMail", "anAddress", SimpleGeo(-123.0, 123.0))
        if (user == null) {
            throw NotFoundResponse("No se encontro el usuario con id $id")
        } else {
            res = UsuarioView(user!!.username, user!!.email, user!!.address, SimpleGeo(user!!.coordenadas.latitude,user!!.coordenadas.longitude))
        }
        return res
    }

    fun getUserOrders(id: Int): UsuarioViewOrder {
        val user = users.firstOrNull { it.id == id }
        var res = UsuarioViewOrder("anName", "anMail", "anAddress", Geo(-123.0, 123.0, "Nothing"), mutableListOf<SimplePedido>())
        if (user == null) {
            throw NotFoundResponse("No se encontro el usuario con id $id")
        } else {
            res = UsuarioViewOrder(user!!.username, user!!.email, user!!.address, user!!.coordenadas, user!!.pedidos)
        }
        return res
    }


    fun loginUser(username: String, password: String): UsuarioSimpleLogin {
        val user = users.firstOrNull { it.username == username && it.password == password }
        if(user == null){
            throw NotFoundResponse("Login fallido")
        }
        return UsuarioSimpleLogin(user.id,user.username,user.coordenadas)
    }

    fun restaurantToRestaurantAPI(): MutableList<RestaurantApi> {
        var resultado = mutableListOf<RestaurantApi>()
        for(restaurant in morfiYa.restaurantes)
            resultado.add(RestaurantApi(++lastIdRestaurant, restaurant.nombre,restaurant.direccion,restaurant.coordenadas,restaurant.descripcion,menusToMenusRestaurant(restaurant.menues)))
        return resultado
    }

    fun addRestaurant(restaurant: Restaurant): RestaurantApi{

        val rest = RestaurantApi(restaurant.codigo.toInt(), restaurant.nombre, restaurant.direccion, restaurant.coordenadas, restaurant.descripcion, menusToMenusRestaurant(restaurant.menues))
        restaurants.add(rest)
        return rest
    }

    fun addRestaurantApi(restaurant: RestaurantApi): RestaurantApi{
        val rest = RestaurantApi(restaurant.id, restaurant.name, restaurant.direction, restaurant.position, restaurant.description, restaurant.menus)
        restaurants.add(rest)
        return rest
    }

    fun getAllRestaurants(): List<RestaurantApi> = restaurants

    fun getRestaurant(id: Int): RestaurantApi{

        val restaurant = restaurants.firstOrNull { it.id == id}
        if(restaurant == null){
            throw NotFoundResponse("No se encontro el restaurant con id $id")
        }else
            return restaurant
    }


    fun addOrder(pedido: Pedido): SimplePedido {  // Los pedidos los almacena la aplicacion morfiYa o cada usuario?
        val order = SimplePedido(++lastIdOrder, ++lastIdRestaurant,pedido.restaurant.nombre, menusToSimpleMenus(pedido.menues), estadoToSimpleEstado(pedido.estado), pedido.calcularPrecioTotalPedido(),medioTarjetaDeCredito(pedido.medioDePago), geoToSimpleGeo(pedido.destino), medioEfectivo(pedido.medioDePago), medioMercadoPago(pedido.medioDePago))
        orders.add(order)
        return order
    }

    fun addOrderApi(pedido: SimplePedido): SimplePedido{
        val order = SimplePedido(++lastIdOrder, pedido.restaurantId, pedido.nombreRestaurant, pedido.menus, pedido.estado, pedido.costoTotalPedido, pedido.tarjetaDeCredito, pedido.destination, pedido.efectivo, pedido.mercadoPago)
        orders.add(order)
        return order
    }

    fun search(text: String): SimpleSearch {
        val menues = morfiYa.buscarMenusPorNombresEnRestauranes(text)
        val restaurants = morfiYa.buscarRestaurantsPorNombre(text)
        var menuesApi = menues.map {MenuRestaurant(it.key.codigo, it.key.nombre, it.key.calcularCostoMenu(), productsToProductosRestaurant(it.key.productos)) }
        var restaurantsApi = restaurants.map { RestaurantApi(it.codigo.toInt(), it.nombre, it.direccion, it.coordenadas, it.descripcion, menusToMenusRestaurant(it.menues)) }
        return SimpleSearch(restaurantsApi.toMutableList(), menuesApi.toMutableList())
    }
}