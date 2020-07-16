package server

data class MenuRestaurant(val id: String, var name: String, var rawPrice: Double, var products: MutableList<ProductRestaurant>)