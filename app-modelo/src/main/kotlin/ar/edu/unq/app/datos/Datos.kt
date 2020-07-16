package ar.edu.unq.app.datos

import ar.edu.unq.app.descuento.SinDescuento
import ar.edu.unq.app.mediosDePago.Efectivo
import ar.edu.unq.app.mediosDePago.MedioDePago
import ar.edu.unq.app.mediosDePago.MercadoPago
import ar.edu.unq.app.mediosDePago.TarjetaDeCredito
import ar.edu.unq.app.modelo.*
import estados.Pendiente
import java.time.LocalDate

class Datos {

    private var producto1 = Producto("000", "Milanesa", "Altas milangas", 40.0, "Plato principal")
    private var producto2 = Producto("001", "Pollo", "Pollo al spiedo", 50.0, "Plato principal")
    private var producto3 = Producto("002", "Pepsi", "bebida sin alcohol", 35.0, "Bebida")
    private var producto4 = Producto("003", "Chorizo", "chori", 50.0, "Plato principal")
    private var producto5 = Producto("004", "Papas fritas", "papas con queso chedar", 45.0, "Guarnición")
    private var producto6 = Producto("005", "Fanta", "bebida sin alcohol", 40.0, "Bebida")
    private var producto7 = Producto("006","Cerveza quilmes", "bebida alcoholica", 50.0, "Bebida" )
    private var producto8 = Producto("007", "Pizza muzzarella", "pizza con muzzarrela y aceitunas",70.0, "Plato principal")
    private var producto9 = Producto("008", "Ensalada", "ensalada con lechuga y tomate", 35.0, "Guarnición")

    private var productosMenu1 : Map<Producto, Int> = mapOf(producto1 to 2, producto3 to 1)
    private var menu1 = Menu("000", "Menu1", "Productos del menu1", productosMenu1,
        SinDescuento(), true)

    private var productosMenu2 : Map<Producto, Int> = mapOf(producto4 to 1, producto5 to 1, producto6 to 1)
    private var menu2 = Menu("001", "Menu2", "Productos del menu2", productosMenu2,
        SinDescuento(), true)

    private var productosMenu3 : Map<Producto, Int> = mapOf(producto4 to 2, producto6 to 1)
    private var menu3 = Menu("002", "Menu3", "Productos del menu3", productosMenu3,
        SinDescuento(), true)

    private var productosMenu4 : Map<Producto, Int> = mapOf(producto1 to 1, producto6 to 1)
    private var menu4 = Menu("003", "Menu4", "Productos del menu4", productosMenu4,
        SinDescuento(), true)

    private var productosMenu5: Map<Producto, Int> = mapOf(producto7 to 1, producto8 to 1, producto9 to 1)
    private var menu5 = Menu("004", "Menu5", "Productos del menu 5", productosMenu5,
        SinDescuento(), true)

    private var productosMenu6: Map<Producto, Int> = mapOf(producto7 to 1, producto8 to 1)
    private var menu6 = Menu("005", "Menu6", "Productos del menu 6", productosMenu6,
        SinDescuento(), true)


    private var geo1 = Geo(100.0, -100.0, "Quilmes")
    private var geo2 = Geo(200.0, -200.0, "Quilmes")
    private var geo3 = Geo(250.0, -250.0, "Quilmes")

    private var mediosDePago1 = mutableListOf<MedioDePago>()
    private var mediosDePago2 = mutableListOf<MedioDePago>()
    private var mediosDePago3 = mutableListOf<MedioDePago>()

    private var productos =  mutableListOf(producto1, producto2, producto3, producto4, producto5, producto6, producto7, producto8, producto9)

    private var menues1 = mutableListOf<Menu>(menu1,menu2)
    private var menues2 = mutableListOf<Menu>(menu3,menu4)
    private var menues3 = mutableListOf<Menu>(menu5,menu6)

    private var restaurant1 = Restaurant("001","Pertutti","Buenos cócteles · Agradable · Informal","Av. Bartolomé Mitre 703",geo1,mediosDePago1,productos,menues1)
    private var restaurant2 = Restaurant("002","Distrito Sur Avellaneda","Comidas durante la madrugada","Juan Bautista Palaá 568",geo2,mediosDePago2,productos,menues2)
    private var restaurant3 = Restaurant("003","Subway","Bocadillos personalizados y ensaladas en mostrador de esta cadena informal, con opciones saludables","25 de Mayo 19",geo3,mediosDePago3,productos,menues3)

    private var menuesPedido1 = mapOf<Menu,Int>(menu1 to 2, menu2 to 1)
    private var menuesPedido2 = mapOf<Menu,Int>(menu3 to 2, menu4 to 1)
    private var menuesPedido3 = mapOf<Menu,Int>(menu1 to 1, menu2 to 3)
    private var menuesPedido4 = mapOf<Menu,Int>(menu5 to 1, menu6 to 4)
    private var menuesPedido5 = mapOf<Menu,Int>(menu3 to 4, menu4 to 2)
    private var menuesPedido6 = mapOf<Menu,Int>(menu5 to 3, menu6 to 2)

    private var pedido1 = Pedido("000", LocalDate.now(),restaurant1,menuesPedido1,Pendiente(),Efectivo(),geo1)
    private var pedido2 = Pedido("001", LocalDate.now(),restaurant2,menuesPedido2,Pendiente(),Efectivo(),geo1)
    private var pedido3 = Pedido("002", LocalDate.now(),restaurant1,menuesPedido3,Pendiente(),Efectivo(),geo2)
    private var pedido4 = Pedido("003", LocalDate.now(),restaurant3,menuesPedido4,Pendiente(),Efectivo(),geo2)
    private var pedido5 = Pedido("004", LocalDate.now(),restaurant2,menuesPedido5,Pendiente(),Efectivo(),geo1)
    private var pedido6 = Pedido("005", LocalDate.now(),restaurant3,menuesPedido6,Pendiente(),Efectivo(),geo1)

    private var pedidos1 = mutableListOf<Pedido>(pedido1,pedido2,pedido3,pedido4)
    private var pedidos2 = mutableListOf<Pedido>(pedido5,pedido6,pedido1,pedido3)

    private var usuario1 = Usuario("Homero","homero@gmail.com","1234", LocalDate.now(),"Av. Siempre Viva 742", geo1,pedidos1)
    private var usuario2 = Usuario("Ned","ned@gmail.com","1234", LocalDate.now(),"Av. Siempre Viva 744",geo2,pedidos2)

    private var usuarioSupervisor1 = UsuarioSupervisor("german","1234",restaurant1)
    private var usuarioSupervisor2 = UsuarioSupervisor("alexander","1234",restaurant2)

    public var restaurantes = mutableListOf<Restaurant>(restaurant1,restaurant2,restaurant3)
    public var usuarios = mutableListOf<Usuario>(usuario1,usuario2)
    public var mediosDePagoApp = mutableListOf<MedioDePago>(Efectivo(), MercadoPago(), TarjetaDeCredito("TarjetaDeCredito","","","","",""))
    public var usuariosSupervisores = mutableListOf<UsuarioSupervisor>(usuarioSupervisor1,usuarioSupervisor2)
}