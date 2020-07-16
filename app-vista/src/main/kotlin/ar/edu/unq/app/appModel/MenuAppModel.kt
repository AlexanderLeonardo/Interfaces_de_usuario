package ar.edu.unq.app.appModel

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.descuento.DescuentoEfectivo
import ar.edu.unq.app.descuento.DescuentoPorcentaje
import ar.edu.unq.app.descuento.SinDescuento
import ar.edu.unq.app.descuento.TipoDescuento
import ar.edu.unq.app.excepciones.MenuException
import ar.edu.unq.app.modelo.Menu
import ar.edu.unq.app.modelo.Producto
import ar.edu.unq.app.modelo.Restaurant
import ar.edu.unq.app.wrapper.MenuWrapper
import ar.edu.unq.app.wrapper.ProductoWrapper
import org.uqbar.commons.model.annotations.Observable

@Observable
class MenuAppModel(var menuSeleccionado: MenuWrapper?, var restaurant: Restaurant?, var morfiYa: MorfiYa, var administracionAppModel: AdministracionAppModel) {

    var listaTiposDeDescuento = mutableListOf<String>("Sin Descuento","Porcentaje","Efectivo")
    var tipoDescuento :String? = menuSeleccionado?.descuento?.nombre
    var estadoMenu = menuSeleccionado?.estado
    var valorDescuento = menuSeleccionado?.descuento?.obtenerDescuento()


    var codigoMenuSeleccionado = menuSeleccionado?.codigo
    var nombreMenuSeleccionado = menuSeleccionado?.nombre
    var descripcionMenuSeleccionado = menuSeleccionado?.descripcion
    var descuentoMenuSeleccionado = menuSeleccionado?.descuento?.obtenerDescuento()

    var codigoMenuNuevo = ""
    var nombreMenuNuevo = ""
    var descripcionMenuNuevo = ""
    var descuentoMenuNuevo = 0.0

    var productosDeMenuSeleccionado = morfiYa.buscarProductosDelMenuRestaurant(restaurant!!.nombre,menuSeleccionado?.nombre)

    var productoSeleccionadoRestaurant : ProductoWrapper? = null
    var productoSeleccionadoMenu : ProductoWrapper? = null

    var listaDeProductosRestaurantModificable = mutableListOf<ProductoWrapper>()

    var listaDeProductosMenuModificable = mutableListOf<ProductoWrapper>()

    fun inicializaListasDeProductosWrapper() {

        listaDeProductosMenuModificable = obtenerProductosWrapper(this.obtenerListaDeProductosDelMenu())

        listaDeProductosRestaurantModificable = obtenerProductosWrapper(morfiYa.buscarProductosRestaurant(restaurant?.nombre))
    }

    fun obtenerProductosWrapper(productos: MutableList<Producto>?): MutableList<ProductoWrapper> {
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

    fun obtenerListaDeProductosDelMenu() : MutableList<Producto> {
        var productos = morfiYa.buscarProductosDelMenuRestaurant(restaurant!!.nombre,menuSeleccionado?.nombre)
        var resultado = mutableListOf<Producto>()
        for((producto,cantidad) in productos) {
            var numeroDeMenues = cantidad
            while(numeroDeMenues != 0) {
                resultado.add(producto)
                numeroDeMenues--
            }
        }

        return resultado
    }

    fun agregarProductoRestaurantAMenu() {
        listaDeProductosMenuModificable = agregarProductoAMenu(productoSeleccionadoRestaurant!!)
    }

    fun agregarProductoAMenu(productoSeleccionado: ProductoWrapper?): MutableList<ProductoWrapper> {
        var resultado = mutableListOf<ProductoWrapper>()
        for(producto in listaDeProductosMenuModificable) {
            resultado.add(producto)
        }
        resultado.add(productoSeleccionado!!)

        return resultado
    }

    fun quitarProductoDelMenu() {
        listaDeProductosMenuModificable = eliminarProductoSeleccionadoEnMenu()
    }

    fun eliminarProductoSeleccionadoEnMenu(): MutableList<ProductoWrapper> {
        var resultado = mutableListOf<ProductoWrapper>()
        var borrar = false
        verificarNoEsUltimoProductoEnMenu()
        for(producto in listaDeProductosMenuModificable) {
            if (!producto.nombre.equals(productoSeleccionadoMenu?.nombre) || borrar) {
                resultado.add(producto)
            } else {
                borrar = true
            }
        }
        return resultado
    }

    fun verificarNoEsUltimoProductoEnMenu() {
        if(listaDeProductosMenuModificable.size.equals(1)){
            throw MenuException("EL menu no puede quedar sin productos")
        }
    }


    fun enabled(): Boolean {
        return codigoMenuSeleccionado == null
    }


    fun agregarNuevoMenu() {
        var productosDeLNuevoMenu = convertirProductos(listaDeProductosMenuModificable)
        verificarMenuSinCodigo(codigoMenuNuevo)
        verificarMenuSinNombre(nombreMenuNuevo)
        verificarMenuSinDescripcion(descripcionMenuNuevo)
        verificarMenuSinProductos(productosDeLNuevoMenu)
        var menu = Menu(codigoMenuNuevo,nombreMenuNuevo,descripcionMenuNuevo,productosDeLNuevoMenu,obtenerDescuento(descuentoMenuNuevo),estadoMenu!!)
        morfiYa.agregarNuevoMenuARestaurant(restaurant!!.nombre,menu)
        administracionAppModel.completarListaDeMenues()
    }

    private fun verificarMenuSinDescripcion(descripcionMenu: String) {
        if(descripcionMenu.equals("")) {
            throw MenuException("El menu debe tener una descripcion")
        }
    }

    private fun verificarMenuSinNombre(nombreMenu: String) {
        if(nombreMenu.equals("")) {
            throw MenuException("El menu debe tener un nombre")
        }
    }

    private fun verificarMenuSinCodigo(codigoMenu: String) {
        if(codigoMenu.equals("")) {
            throw MenuException("El menu debe tener un codigo")
        }
    }

    private fun verificarMenuSinProductos(productos: Map<Producto,Int>) {
        if(productos.isEmpty()) {
            throw MenuException("EL menu debe tener productos")
        }
    }

    private fun convertirProductos(productos: MutableList<ProductoWrapper>): Map<Producto,Int> {
        var map = mapOf<Producto,Int>()
        var set = productos.toSet()
        for(productoWrapper in set) {
            var cantidad = productos.count { it.nombre!!.equals(productoWrapper.nombre) }
            var producto = morfiYa.obtenerProducto(restaurant!!.nombre,productoWrapper.codigo!!)
            map+=(producto!! to cantidad)
        }
        return map
    }

    private fun obtenerDescuento(descuento: Double) : TipoDescuento {
        when (tipoDescuento) {
            "Efectivo" -> return DescuentoEfectivo(descuento)
            "Porcentaje"-> return DescuentoPorcentaje(descuento)
            else -> {
                return SinDescuento()
            }
        }
    }

    fun modificarMenu() {
        var productosMenuAModificar = convertirProductos(listaDeProductosMenuModificable)
        verificarMenuSinNombre(nombreMenuSeleccionado!!)
        verificarMenuSinDescripcion(descripcionMenuSeleccionado!!)
        verificarMenuSinProductos(productosMenuAModificar)
        morfiYa.modificarMenuARestaurant(restaurant!!.nombre,codigoMenuSeleccionado!!,nombreMenuSeleccionado!!,descripcionMenuSeleccionado!!,productosMenuAModificar,obtenerDescuento(descuentoMenuSeleccionado!!),estadoMenu!!)
        administracionAppModel.completarListaDeMenues()
    }

    fun eliminarMenu() {
        morfiYa.eliminarMenu(restaurant!!.nombre,codigoMenuSeleccionado)
        administracionAppModel.completarListaDeMenues()
    }
}