package com.bikash.data

import com.bikash.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImp(
    private val db: CoroutineDatabase
): MessageDataSource {


    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}