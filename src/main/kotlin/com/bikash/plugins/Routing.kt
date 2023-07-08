package com.bikash.plugins

import com.bikash.room.RoomController
import com.bikash.routes.chatSocket
import com.bikash.routes.getAllMessages
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing){
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
