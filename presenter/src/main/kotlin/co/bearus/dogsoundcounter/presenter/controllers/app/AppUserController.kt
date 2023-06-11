package co.bearus.dogsoundcounter.presenter.controllers.app

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.*
import co.bearus.dogsoundcounter.presenter.parallelMap
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.message.MessageRepository
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.RoomUserRepository
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserDashboardUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserRoomsUseCase
import co.bearus.dogsoundcounter.usecases.user.RefreshUserWithTokenUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.AuthUserWithProviderUseCase
import co.bearus.dogsoundcounter.usecases.user.oauth.TokenProvider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/users")
class AppUserController(
    private val authUserWithProvider: AuthUserWithProviderUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val getUserDashboard: GetUserDashboardUseCase,
    private val refreshUserWithToken: RefreshUserWithTokenUseCase,
    private val getUserRooms: GetUserRoomsUseCase,
    private val tokenProvider: TokenProvider,
    private val messageRepository: MessageRepository,
    private val roomRepository: RoomRepository,
) {
    @PostMapping("/oauth")
    suspend fun authenticateUser(
        @RequestBody request: AuthenticationRequest,
    ) = withUseCase(
        useCase = authUserWithProvider,
        param = request.to(),
        mappingFunction = AuthenticationResultResponse::from,
    )

    @PostMapping("/oauth/test")
    suspend fun testAuth() = withUseCase(
        useCase = getUserById,
        param = "01H256SJWQXX11JYFNQJS2RTEZ",
        mappingFunction = {
            AuthenticationResultResponse(
                accessToken = tokenProvider.createAccessToken(it),
                refreshToken = tokenProvider.createRefreshToken(it),
            )
        },
    )

    @GetMapping("/me")
    suspend fun getMe(
        @RequestUser user: LoginUser,
    ) = withUseCase(
        useCase = getUserById,
        param = user.userId,
        mappingFunction = UserResponse::from,
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

    @PostMapping("/refresh")
    suspend fun refresh(
        @RequestBody dto: RefreshTokenRequest,
    ) = withUseCase(
        useCase = refreshUserWithToken,
        param = dto.to()
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
                    createdAtTs = room.createdAtTs
                )
            }
        }
    )
}
