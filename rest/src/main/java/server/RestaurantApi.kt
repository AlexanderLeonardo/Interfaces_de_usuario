package server

import ar.edu.unq.app.modelo.Geo


class RestaurantApi(val id: Int, var name: String, var direction: String, var position: Geo, var description: String, var menus: MutableList<MenuRestaurant> ){

    fun addMenu(menu: MenuRestaurant){
        menus.add(menu)
    }
}