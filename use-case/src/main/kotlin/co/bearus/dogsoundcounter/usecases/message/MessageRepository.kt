package co.bearus.dogsoundcounter.usecases.message

import co.bearus.dogsoundcounter.entities.Message

interface MessageRepository {
    suspend fun persist(message: Message): Message
}