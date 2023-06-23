package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.*
import co.bearus.dogsoundcounter.presenter.parallelMap
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserPriceRepository
import co.bearus.dogsoundcounter.usecases.user.*
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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
    private val createNewFriend: CreateNewFriendUseCase,
    private val getUserByTag: GetUserByTagUseCase,
    private val updateNotificationConfig: UpdateNotificationConfigUseCase,
    private val acceptUserFriendRequest: AcceptUserFriendRequestUseCase,
    private val denyUserFriendRequest: DenyUserFriendRequestUseCase,
    private val breakFriend: BreakFriendUseCase,
    private val userNotificationRepository: UserNotificationRepository,
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
        @RequestParam(required = false) weekDay: LocalDate?,
    ) = withUseCase(
        useCase = getUserDashboard,
        param = GetUserDashboardUseCase.Input(
            user = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            weekDay = weekDay,
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

    @DeleteMapping("/friends")
    suspend fun breakFriend(
        @RequestUser user: LoginUser,
        @RequestBody dto: BreakFriendRequest,
    ) = withUseCase(
        useCase = breakFriend,
        param = BreakFriendUseCase.Input(
            me = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            target = withUseCase(
                useCase = getUserById,
                param = dto.userId,
            ),
        )
    )

    @PostMapping("/friends/requests/accept")
    suspend fun acceptRequest(
        @RequestUser user: LoginUser,
        @RequestBody dto: CreateNewFriendRequest,
    ) = withUseCase(
        useCase = acceptUserFriendRequest,
        param = AcceptUserFriendRequestUseCase.Input(
            from = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            target = withUseCase(
                useCase = getUserByTag,
                param = dto.tag,
            ),
        )
    )

    @PostMapping("/friends/requests/deny")
    suspend fun denyRequest(
        @RequestUser user: LoginUser,
        @RequestBody dto: CreateNewFriendRequest,
    ) = withUseCase(
        useCase = denyUserFriendRequest,
        param = DenyUserFriendRequestUseCase.Input(
            from = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            target = withUseCase(
                useCase = getUserByTag,
                param = dto.tag,
            ),
        )
    )

    @GetMapping("/friends/requests")
    suspend fun getFriendRequests(
        @RequestUser user: LoginUser,
    ) = roomUserFriendRepository.getReceivedRequestIds(user.userId).parallelMap {
        withUseCase(
            useCase = getUserById,
            param = it,
            mappingFunction = UserResponse::from,
        )
    }

    @PostMapping("/friends/requests")
    suspend fun createFriend(
        @RequestUser user: LoginUser,
        @RequestBody dto: CreateNewFriendRequest,
    ) = withUseCase(
        useCase = createNewFriend,
        param = CreateNewFriendUseCase.Input(
            user1 = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            user2 = withUseCase(
                useCase = getUserByTag,
                param = dto.tag,
            ),
        ),
    )

    @GetMapping("/notification")
    suspend fun getNotificationConfig(
        @RequestUser user: LoginUser,
    ) = userNotificationRepository.getByUserId(
        user.userId
    )

    @PostMapping("/notification")
    suspend fun updateNotificationConfig(
        @RequestUser user: LoginUser,
        @RequestBody dto: UpdateNotificationRequest,
    ) = withUseCase(
        useCase = updateNotificationConfig,
        param = UpdateNotificationConfigUseCase.Input(
            user = withUseCase(
                useCase = getUserById,
                param = user.userId
            ),
            type = dto.type,
            value = dto.value,
        )
    )
}
