package com.bikash.di

import com.bikash.data.MessageDataSource
import com.bikash.data.MessageDataSourceImp
import com.bikash.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("message_db")
    }
    single<MessageDataSource>{
        MessageDataSourceImp(get())
    }

    single {
        RoomController(get())
    }
}