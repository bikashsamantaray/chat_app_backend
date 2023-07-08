package com.bikash.room

import com.bikash.data.MessageDataSource
import com.bikash.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userName: String,
        sessionId: String,
        socket: WebSocketSession
    ){
        if (members.containsKey(userName)){
            throw MembersAlreadyExistsException()
        }else{
            members[userName] = Member(
                userName = userName,
                sessionID = sessionId,
                socket = socket
            )
        }
    }

    suspend fun sendMessage(senderUserName: String, message: String){
        members.values.forEach{member ->
            val messageEntity = Message(
                text = message,
                username = senderUserName,
                timestamp = System.currentTimeMillis()
            )
            messageDataSource.insertMessage(messageEntity)

            val parsedMessage = Json.encodeToString(messageEntity)
            member.socket.send(Frame.Text(parsedMessage))

        }
    }

    suspend fun getAllMessages(): List<Message>{
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String){
        members[username]?.socket?.close()
        if (members.containsKey(username)){
            members.remove(username)
        }

    }


}