package co.bearus.dogsoundcounter.usecases.user

interface FriendRepository {
    suspend fun getFriends(userId: String): List<String>
    suspend fun makeFriend(userId1: String, userId2: String)
    suspend fun isFriend(userId1: String, userId2: String): Boolean
    suspend fun breakFriend(userId1: String, userId2: String): Boolean
    suspend fun getReceivedRequestIds(userId: String): List<String>
    suspend fun getSentRequestIds(userId: String): List<String>
    suspend fun sendRequest(fromId: String, toId: String)
    suspend fun clearRequest(fromId: String, toId: String): Boolean
}