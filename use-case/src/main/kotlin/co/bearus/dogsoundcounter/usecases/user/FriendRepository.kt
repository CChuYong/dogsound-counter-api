package co.bearus.dogsoundcounter.usecases.user

interface FriendRepository {
    suspend fun getFriends(userId: String): List<String>
    suspend fun makeFriend(userId1: String, userId2: String)
}