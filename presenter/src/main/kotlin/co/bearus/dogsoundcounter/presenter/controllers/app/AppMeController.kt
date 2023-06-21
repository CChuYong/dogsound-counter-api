package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.RoomDetailResponse
import co.bearus.dogsoundcounter.presenter.dto.UpdateUserNicknameRequest
import co.bearus.dogsoundcounter.presenter.dto.UpdateUserProfileImageRequest
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.parallelMap
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import co.bearus.dogsoundcounter.usecases.user.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/me")
class AppMeController(
    private val getUserById: GetUserByIdUseCase,
    private val updateNickname: UpdateNicknameUseCase,
    private val messageRepository: MessageRepository,
    private val roomRepository: RoomRepository,
    private val roomUserPriceRepository: RoomUserPriceRepository,
    private val roomUserFriendRepository: FriendRepository,
    private val getUserDashboard: GetUserDashboardUseCase,
    private val getUserRooms: GetUserRoomsUseCase,
    private val updateUserImage: UpdateUserImageUseCase,
) {
    @GetMapping
    suspend fun getMe(
        @RequestUser user: LoginUser,
    ) = withUseCase(
        useCase = getUserById,
        param = user.userId,
        mappingFunction = UserResponse::from,
    )

    @PostMapping("/nickname")
    suspend fun updateNickname(
        @RequestUser user: LoginUser,
        @RequestBody dto: UpdateUserNicknameRequest,
    ) = withUseCase(
        useCase = updateNickname,
        param = UpdateNicknameUseCase.Input(
            user = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            newNickName = dto.newNickname,
        ),
        mappingFunction = UserResponse::from
    )

    @GetMapping("/dashboard")
    suspend fun getDashboard(
        @RequestUser user: LoginUser,
    ) = withUseCase(
        useCase = getUserDashboard,
        param = GetUserDashboardUseCase.Input(
            withUseCase(
                useCase = getUserById,
                param = user.userId,
            )
        ),
    )

    @GetMapping("/rooms")
    suspend fun getRooms(
        @RequestUser user: LoginUser,
    ) = withUseCase(
        useCase = getUserRooms,
        param = GetUserRoomsUseCase.Input(
            withUseCase(
                useCase = getUserById,
                param = user.userId,
            )
        ),
        mappingFunction = {
            it.list.parallelMap { roomUser ->
                val prices = roomUserPriceRepository.sumByRoomUser(roomUserId = roomUser.roomUserId)
                val room = roomRepository.getById(roomUser.roomId)
                val lastMessage = messageRepository.getLastMessage(roomUser.roomId)
                val unreadMessageCount = messageRepository.countUnreadMessage(
                    roomId = room.roomId,
                    messageId = roomUser.lastReadMessageId ?: "",
                )
                RoomDetailResponse(
                    roomId = room.roomId,
                    roomName = room.roomName,
                    ownerId = room.ownerId,
                    lastMessageAtTs = lastMessage?.createdAtTs ?: room.createdAtTs,
                    unreadMessageCount = unreadMessageCount,
                    roomImageUrl = room.roomImageUrl,
                    cumulatedPrice = prices,
                    createdAtTs = room.createdAtTs,
                )
            }.sortedByDescending { detail ->
                detail.lastMessageAtTs
            }
        }
    )

    @PostMapping("/profile-image")
    suspend fun updateProfileImage(
        @RequestUser user: LoginUser,
        @RequestBody dto: UpdateUserProfileImageRequest,
    ) = withUseCase(
        useCase = updateUserImage,
        param = UpdateUserImageUseCase.Input(
            user = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            imageUrl = dto.imageUrl,
        ),
        mappingFunction = UserResponse::from,
    )

    @GetMapping("/friends")
    suspend fun getFriends(
        @RequestUser user: LoginUser,
    ) = roomUserFriendRepository.getFriends(user.userId).parallelMap {
        withUseCase(
            useCase = getUserById,
            param = it,
            mappingFunction = UserResponse::from,
        )
    }
}