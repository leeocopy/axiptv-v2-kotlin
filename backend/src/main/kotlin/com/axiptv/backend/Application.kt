package com.axiptv.backend

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val logger = LoggerFactory.getLogger("Application")
    
    install(ContentNegotiation) {
        json()
    }

    try {
        initDatabase()
    } catch (e: Exception) {
        logger.error("Failed to initialize database", e)
    }

    val deviceService = DeviceService()

    routing {
        get("/health") {
            call.respond(mapOf("status" to "up"))
        }

        post("/api/device/status") {
            try {
                val request = call.receive<DeviceStatusRequest>()
                val response = deviceService.resolveStatus(request.device_hash)
                call.respond(response)
            } catch (e: Exception) {
                logger.error("Error processing device status", e)
                call.respond(io.ktor.http.HttpStatusCode.InternalServerError, mapOf("error" to "Internal server error"))
            }
        }
    }
}
