package ar.edu.unq.app.modelo

data class Geo(val latitude: Double, val longitude: Double, val name: String = "")

object GeoCalculator {
    private const val EARTH_RADIUS: Double = 6371.0

    fun distance(geo1: Geo, geo2: Geo): Double {
        val dLat = deg2rad(geo2.latitude - geo1.latitude)
        val dLon = deg2rad(geo2.longitude - geo1.longitude)
        val temp1: Double = Math.pow(Math.sin(dLat / 2), 2.0)
        val temp2: Double = Math.cos(deg2rad(geo1.latitude)) * Math.cos(deg2rad(geo2.latitude)) * Math.pow(
            Math.sin(dLon / 2),
            2.0
        )
        val a = temp1 + temp2
        val aTan: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val res = aTan * EARTH_RADIUS
        return Math.floor(res * 100) / 100
    }

    private fun deg2rad(dec: Double): Double = dec * (Math.PI / 180)
}