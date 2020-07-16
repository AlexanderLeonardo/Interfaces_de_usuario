package ar.edu.unq.app.appModel

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.modelo.Menu
import ar.edu.unq.app.modelo.Producto
import ar.edu.unq.app.modelo.Restaurant
import ar.edu.unq.app.wrapper.MenuWrapper
import ar.edu.unq.app.wrapper.ProductoWrapper
import org.uqbar.commons.model.annotations.Observable

@Observable
class AdministracionAppModel(var morfiYa: MorfiYa) {

    var restaurant: Restaurant? = null

    var filtroProducto = ""
    var listaDeProductos = mutableListOf<ProductoWrapper>()
    var productoSeleccionado: ProductoWrapper? = null
    var productoNuevo: ProductoWrapper = ProductoWrapper()

    var filtroMenu = ""
    var listaDeMenues = mutableListOf<MenuWrapper>()
    var menuSeleccionado: MenuWrapper? = null
    var menuNuevo: MenuWrapper = MenuWrapper()

    fun completarListaDeProductos(){
        var productos = obtenerProductosWrapper(morfiYa.buscarProductosRestaurant(restaurant?.nombre))
        listaDeProductos = productos
    }

    fun obtenerProductosWrapper(productos: MutableList<Producto>): MutableList<ProductoWrapper> {
        var resultList = mutableListOf<ProductoWrapper>()
        for(producto in productos.orEmpty()) {
            var productoWrapper = ProductoWrapper()
            productoWrapper.producto = producto
            productoWrapper.nombre = producto.nombre
            productoWrapper.precio = producto.precio
            productoWrapper.codigo = producto.codigo
            productoWrapper.descripcion = producto.descripcion
            productoWrapper.categoria = producto.categoria
            resultList.add(productoWrapper)
        }
        return resultList
    }

    fun completarListaDeMenues() {
        var menues = obtenerMenuesWrapper(morfiYa.buscarMenuesRestaurant(restaurant!!.nombre))
        listaDeMenues = menues
    }

    fun obtenerMenuesWrapper(menues: MutableList<Menu>): MutableList<MenuWrapper> {
        var resultList = mutableListOf<MenuWrapper>()
        for(menu in menues.orEmpty()) {
            var menuWrapper = MenuWrapper()
            menuWrapper.menu = menu
            menuWrapper.nombre = menu.nombre
            menuWrapper.productos =  menu?.productos
            menuWrapper.codigo = menu.codigo
            menuWrapper.descripcion = menu.descripcion
            menuWrapper.descuento = menu.descuento
            menuWrapper.estado = menu.estado
            resultList.add(menuWrapper)
        }
        return resultList
    }

    fun buscarProductosPorTexto() {
        var productos = morfiYa.buscarProductosEnRestaurantPorTextoBusqueda(restaurant!!.nombre,filtroProducto)
        var productosWrapper = obtenerProductosWrapper(productos)
        listaDeProductos = productosWrapper

    }

    fun buscarMenuesPorTexto() {
        var menues = morfiYa.buscarMenuesEnRestaurantPorTextoBusqueda(restaurant!!.nombre,filtroMenu)
        var menuesWrapper = obtenerMenuesWrapper(menues)
        listaDeMenues = menuesWrapper
    }
}