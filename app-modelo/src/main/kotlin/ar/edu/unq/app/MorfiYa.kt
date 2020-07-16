package ar.edu.unq.app

import ar.edu.unq.app.datos.Datos
import ar.edu.unq.app.descuento.TipoDescuento
import ar.edu.unq.app.modelo.*
import java.lang.Exception

class MorfiYa {

    private val datos = Datos()

    public var restaurantes = datos.restaurantes
    public var usuarios = datos.usuarios
    public var mediosDePagoApp = datos.mediosDePagoApp
    public var usuarioSupervisores = datos.usuariosSupervisores

    fun agregarRestaurant(restaurant: Restaurant): Unit {
        restaurantes.add(restaurant)
    }


    fun agregarUsuario(usuario: Usuario): Unit {
        usuarios.add(usuario)
    }


    fun buscarRestaurantPorCodigo(cod: String): Restaurant?{
        // Busqueda exacta (el restaurant se encuentra si o si en la aplicacion.
        val res: Restaurant? = restaurantes.find { it.codigo == cod }
        return res
    }

    fun buscarRestaurantPorNombre(name: String): Restaurant?{
        // Busqueda parcial (el restaurant se encuentra si o si en la aplicacion.
        val res: Restaurant? = restaurantes.find { it.nombre.contains(name) }
        return res
    }

    fun buscarRestaurantPorCodigoYNombre(dato: String): Restaurant?{
        // Busca restaurant tanto por codigo como por nombre
        val res: Restaurant? = restaurantes.find { it.codigo == dato || it.nombre.contains(dato) }
        return res
    }


    fun buscarRestaurantsPorNombre(name: String): MutableList<Restaurant> {
        // Busca restaurants por nombre en la aplicacion
        var res: List<Restaurant> = listOf()
        res = restaurantes.filter { it.nombre.contains(name) }

        if(name.equals("")) {
            res = restaurantes
        }
        return res.toMutableList()
    }

    fun buscarMenusPorNombresEnRestauranes(name: String): Map<Menu, Restaurant>{
        // Busca menus por nombre en todos los restaurantes

        var res: Map<Menu,Restaurant> = mapOf()
        var menus: List<Menu> = listOf()

        for(restaurant in restaurantes){
                menus = restaurant.buscarMenus(name)
                for(menu in menus){
                    res+=(menu to restaurant)
                }
            }
        return res
    }


    fun buscarMenusPorNombreEnRestaurant(name: String, restaurant: Restaurant): List<Menu> {
        // Busca menus por nombre en un restaurant dado
        var res: List<Menu> = listOf()
        res = restaurant.buscarMenus(name)
        return res
    }


    fun buscarMenusRestaurantsPorNombre(name: String): MutableList<Any> {
        // Busca tanto menus como restaurants por nombre
        var res: MutableList<Any> = mutableListOf()
        var menus: List<Menu> = listOf()
        var restaurants: List<Restaurant> = listOf()
        for(restaurant in restaurantes){
            menus = restaurant.buscarMenus(name)
            for(menu in menus){
                res.add(menu)
            }
        }
        restaurants = restaurantes.filter { it.nombre.contains(name) }
        for (rest in restaurants){
            res.add(rest)
        }

        return res
    }

    fun realizarPedido(username: String, pedido: Pedido) {
        var usuario = buscarUsuario(username)
        usuario!!.pedidosRealizados.add(pedido)
    }

    fun buscarUsuario(username: String): Usuario? {
        return usuarios.find { it.nombre.equals(username) }
    }

    fun obtenerProducto(nombreRestaurant: String,codigoProducto: String) : Producto? {
        var productos = buscarProductosRestaurant(nombreRestaurant)
        return productos.find { it.codigo.equals(codigoProducto) }
    }

    fun validarUsuarioSupervisor(nombreUsuario:String, contrasena:String) : Restaurant? {
        var restaurant: Restaurant? = null
        for(usuarioSupervisor in usuarioSupervisores) {
            if(usuarioSupervisor.nombre.equals(nombreUsuario) && usuarioSupervisor.contrasena.equals(contrasena)) {
                restaurant = usuarioSupervisor.restaurant
            }
        }
        return restaurant
    }


    fun buscarProductosRestaurant(nombreRestaurant: String?) : MutableList<Producto> {
        var productos = mutableListOf<Producto>()
        for(restaurant in restaurantes) {
            if(restaurant.nombre == nombreRestaurant) {
                productos =  restaurant.productos
            }
        }
        return productos
    }

    fun guardarProductoEnRestaurant(nombreRestaurant: String?,producto: Producto) {
        var productos = buscarProductosRestaurant(nombreRestaurant)
        productos.add(producto)
    }

    fun modificarProducto(nombreRestaurant: String,codigo:String,nombre:String,descripcion:String,precio:Double,categoria:String) {
        var productos= buscarProductosRestaurant(nombreRestaurant)
        for(producto in productos) {
            if(producto.codigo == codigo) {
                producto.nombre = nombre
                producto.descripcion = descripcion
                producto.precio = precio
                producto.categoria = categoria
            }
        }
    }

    fun buscarMenuesRestaurant(nombreRestaurant: String) : MutableList<Menu> {
        var menues = mutableListOf<Menu>()
        for(restaurant in restaurantes) {
            if(restaurant.nombre == nombreRestaurant) {
                menues =  restaurant.menues
            }
        }
        return menues

    }

    fun obtenerMenuesAsociadosAProductoDeRestaurant(codigoProducto: String?,nombreRestaurant: String) : MutableList<Menu> {
        var menuesAsociados = mutableListOf<Menu>()
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        for(menu in menues) {
            var menuAsociado = verificarProductoAsociadoAMenu(codigoProducto,menu)
            if(menuAsociado != null) {
                menuesAsociados.add(menuAsociado)
            }
        }

        return menuesAsociados
    }

    fun verificarProductoAsociadoAMenu(codigoProducto: String?, menu: Menu) : Menu?{
        for((producto,cantidad) in menu.productos) {
            if(producto.codigo == codigoProducto ) {
                return menu
            }
        }
        return null
    }

    fun eliminarProducto(nombreRestaurant: String,codigoProducto: String) {
        if(obtenerMenuesAsociadosAProductoDeRestaurant(codigoProducto,nombreRestaurant).isNotEmpty()) {
            throw Exception("No es posible eliminar el producto")
        } else {
            var productos = buscarProductosRestaurant(nombreRestaurant)
            productos.removeAll({ it.codigo.equals(codigoProducto) })
        }
    }

    fun buscarProductosDelMenuRestaurant(nombreRestaurant: String,nombreMenu: String?) : Map<Producto,Int> {
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        var productos = mapOf<Producto,Int>()
        for(menu in menues) {
            if(menu.nombre == nombreMenu) {
                productos = menu.productos
            }
        }
        return productos
    }

    fun eliminarMenu(nombreRestaurant: String,codigoMenu:String?) {
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        menues.removeAll { it.codigo.equals(codigoMenu) }
    }

    fun obtenerTodosLosProductosDelRestaurant(nombreRestaurant: String) :MutableList<Producto> {
        var productos = buscarProductosRestaurant(nombreRestaurant)
        return productos
    }

    fun obtenerTodosLosProductosDelMenu(nombreRestaurant: String,nombreMenu: String?) : Map<Producto,Int> {
        var productos = buscarProductosDelMenuRestaurant(nombreRestaurant,nombreMenu)
        return productos
    }

    fun agregarNuevoMenuARestaurant(nombreRestaurant: String,menu: Menu) {
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        menues.add(menu)
    }

    fun modificarMenuARestaurant(nombreRestaurant: String,codigoMenu:String,nombreMenu: String,descripcionMenu:String,productos:Map<Producto,Int>,tipoDescuento:TipoDescuento,estadoMenu:Boolean) {
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        for(menu in menues) {
            if(menu.codigo == codigoMenu) {
                menu.nombre = nombreMenu
                menu.descripcion = descripcionMenu
                menu.productos = productos
                menu.descuento = tipoDescuento
                menu.estado = estadoMenu
            }
        }
    }

    fun buscarProductosEnRestaurantPorTextoBusqueda(nombreRestaurant: String,textoBusqueda:String): MutableList<Producto> {
        var productos = buscarProductosRestaurant(nombreRestaurant)
        if(textoBusqueda == "") {
                return productos
            } else {
            var resultado = productos.filter { it.nombre.contains(textoBusqueda, ignoreCase = true) }
            return resultado.toMutableList()
        }
    }

    fun buscarMenuesEnRestaurantPorTextoBusqueda(nombreRestaurant: String,textoBusqueda:String): MutableList<Menu> {
        var menues = buscarMenuesRestaurant(nombreRestaurant)
        if(textoBusqueda == "") {
            return menues
        } else {
            var resultado = menues.filter { it.nombre.contains(textoBusqueda, ignoreCase = true) }
            return resultado.toMutableList()
        }
    }


}