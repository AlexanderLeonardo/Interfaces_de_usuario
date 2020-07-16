package server

import ar.edu.unq.app.MorfiYa
import io.javalin.Javalin
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.eclipse.jetty.http.HttpStatus.*



fun main(){

    val app = Javalin.create()
            .enableRouteOverview("/routes")
            .enableCorsForAllOrigins()
            .exception(MismatchedInputException::class.java) { e, ctx ->
                ctx.status(BAD_REQUEST_400)
                ctx.json(mapOf(
                        "status" to BAD_REQUEST_400,
                        "message" to e.message
                ))
            }
            .start(7002)

     //ctx = Context
     //ctx.req -> Request
     //ctx.res -> Response
    app.get("/") { ctx ->
        ctx.json("Hello World")
    }

    val morfiYa = MorfiYa()
    val morfiYaController = MorfiYaController(morfiYa)

    app.get("users"){ ctx ->
        ctx.json(morfiYaController.getAllUsers())
    }

    app.post("login"){ ctx ->
        val user = ctx.body<UsuarioLogin>()
        ctx.json (morfiYaController.loginUser(user.username, user.password))
        ctx.status(CREATED_201)
    }

    app.post("register") {ctx ->
        val user = ctx.body<UsuarioRegister>()
        ctx.json(morfiYaController.addUserApi(user))
        ctx.status(CREATED_201)
    }

    app.get("users/:id") { ctx ->
        val order = ctx.queryParam("include", "")
        val id = ctx.pathParam("id").toInt()
        if(order != "pedidos") {
            ctx.json(morfiYaController.getUser(id))
        }else{
            ctx.json(morfiYaController.getUserOrders(id))
        }
    }


    app.get("search") {
        val text = it.queryParam("text", "")
        println(text)
        it.json(morfiYaController.search(text ?:""))
    }

    app.get("restaurants"){ ctx ->
        ctx.json(morfiYaController.getAllRestaurants())
    }

    app.get("restaurant/:id"){ ctx ->
        val id = ctx.pathParam("id").toInt()
        ctx.json(morfiYaController.getRestaurant(id))
    }

    app.post("deliver/"){ ctx ->
        val order = ctx.body<SimplePedido>()
        ctx.json(morfiYaController.addOrderApi(order))
        ctx.status(CREATED_201)
    }


    app.post("deliver/:id"){ ctx ->
        val id = ctx.pathParam("id").toInt()
        val order = ctx.body<SimplePedido>()
        val usuario = morfiYaController.getUserOrders(id);
        ctx.json(usuario.pedidos.add(order))
    }


}