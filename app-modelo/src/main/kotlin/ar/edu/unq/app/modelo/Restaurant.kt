package ar.edu.unq.app.modelo

import ar.edu.unq.app.mediosDePago.MedioDePago

class Restaurant(val codigo: String, val nombre: String, val descripcion: String, val direccion: String, val coordenadas: Geo, val mediosDePago: List<MedioDePago>, val productos: MutableList<Producto>, var menues: MutableList<Menu>) {

    fun agregarProducto(producto: Producto): Unit{
        productos.add(producto)
    }

    fun agregarMenu(menu: Menu) {
        menues.add(menu)
    }

    fun buscarMenus (nombre: String): List<Menu>{

        var menusRes: List<Menu> = menues.filter { it.nombre.contains(nombre,ignoreCase = true) }
        if(nombre.equals("")) {
            menusRes = menues
        }
        return menusRes
    }

}