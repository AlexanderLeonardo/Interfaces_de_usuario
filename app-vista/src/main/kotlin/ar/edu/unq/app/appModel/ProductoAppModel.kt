package ar.edu.unq.app.appModel

import ar.edu.unq.app.MorfiYa
import ar.edu.unq.app.excepciones.ProductoException
import ar.edu.unq.app.modelo.Producto
import ar.edu.unq.app.wrapper.ProductoWrapper
import org.uqbar.commons.model.annotations.Observable
import java.lang.Exception

@Observable
class ProductoAppModel(var producto: ProductoWrapper, var nombreRestaurant: String?, var morfiYa: MorfiYa, var administracionAppModel: AdministracionAppModel) {

    var listaCategorias = mutableListOf<String>("Plato principal","Entrada","Bebida","Guarnición","Sandwich")

    var codigoProductoSeleccionado = producto?.codigo
    var nombreProductoSeleccionado = producto?.nombre
    var descripcionProductoSeleccionado = producto?.descripcion
    var precioProductoSeleccionado = producto?.precio ?:0.0
    var categoriaProductoSeleccionado = producto?.categoria

    var codigoProductoAGuardar = ""
    var nombreProductoAGuardar = ""
    var descripcionProductoAGuardar = ""
    var precioProductoAGuardar = 0.0
    var categoriaProductoAGuardar = ""

    var listaDeMenuesDeProductoSeleccionado = morfiYa.obtenerMenuesAsociadosAProductoDeRestaurant(codigoProductoSeleccionado,nombreRestaurant!!)

    fun enabled() : Boolean {
        return codigoProductoSeleccionado == null
    }

    fun guardarProducto() {
        verificarProductoSinCodigo(codigoProductoAGuardar)
        verificarProductoSinNombre(nombreProductoAGuardar)
        verificarProductoSinDescripcion(descripcionProductoAGuardar)
        verificarProductoSinPrecio(precioProductoAGuardar)
        verificarProductoSinCategoria(categoriaProductoAGuardar)
        var producto = Producto(codigoProductoAGuardar,nombreProductoAGuardar,descripcionProductoAGuardar,precioProductoAGuardar,categoriaProductoAGuardar)
        morfiYa.guardarProductoEnRestaurant(nombreRestaurant,producto)
        administracionAppModel.completarListaDeProductos()
        administracionAppModel.completarListaDeMenues()
    }

    private fun verificarProductoSinCodigo(codigoProducto: String) {
        if(codigoProducto.equals("")) {
            throw ProductoException("El producto debe tener un código")
        }
    }

    private fun verificarProductoSinNombre(nombreProducto: String) {
        if(nombreProducto.equals("")) {
            throw ProductoException("El producto debe tener un nombre")
        }
    }

    private fun verificarProductoSinDescripcion(descripcionProducto: String) {
        if(descripcionProducto.equals("")) {
            throw ProductoException("EL producto debe tener una descripcion")
        }
    }

    private fun verificarProductoSinPrecio(precioProducto: Double) {
        if(precioProducto.equals(0.0)) {
            throw ProductoException("El producto debe tener un precio mayor a cero")
        }
    }

    private fun verificarProductoSinCategoria(categoriaProducto: String) {
        if(categoriaProducto.equals("")) {
            throw ProductoException("EL producto debe tener una categoria asociada")
        }
    }

    fun modificarProducto() {
        verificarProductoSinNombre(nombreProductoSeleccionado!!)
        verificarProductoSinDescripcion(descripcionProductoSeleccionado!!)
        verificarProductoSinPrecio(precioProductoSeleccionado!!)
        verificarProductoSinCategoria(categoriaProductoSeleccionado!!)
        morfiYa.modificarProducto(nombreRestaurant!!,codigoProductoSeleccionado!!,nombreProductoSeleccionado!!,descripcionProductoSeleccionado!!,precioProductoSeleccionado!!,categoriaProductoSeleccionado!!)
        administracionAppModel.completarListaDeProductos()
        administracionAppModel.completarListaDeMenues()
    }

    fun eliminarProducto() {
        try {
            morfiYa.eliminarProducto(nombreRestaurant!!,codigoProductoSeleccionado!!)
            administracionAppModel.completarListaDeProductos()
        }
        catch (e: Exception) {
            throw ProductoException("El producto ${nombreProductoSeleccionado} se encuentra asociado a algun menú en el sistema ")
        }
    }
}