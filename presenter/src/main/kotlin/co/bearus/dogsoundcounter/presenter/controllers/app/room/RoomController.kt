package co.bearus.dogsoundcounter.presenter.controllers.app.room

import co.bearus.dogsoundcounter.presenter.LoginUser
import co.bearus.dogsoundcounter.presenter.RequestUser
import co.bearus.dogsoundcounter.presenter.dto.CreateNewRoomRequest
import co.bearus.dogsoundcounter.presenter.dto.UpdateRoomImageRequest
import co.bearus.dogsoundcounter.presenter.dto.UserResponse
import co.bearus.dogsoundcounter.presenter.withUseCase
import co.bearus.dogsoundcounter.usecases.room.CreateNewRoomUseCase
import co.bearus.dogsoundcounter.usecases.room.GetRoomUsersUseCase
import co.bearus.dogsoundcounter.usecases.room.RoomRepository
import co.bearus.dogsoundcounter.usecases.room.UpdateRoomImageUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByIdUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/rooms")
class RoomController(
    private val createNewRoom: CreateNewRoomUseCase,
    private val getUserById: GetUserByIdUseCase,
    private val getRoomUsers: GetRoomUsersUseCase,
    private val updateRoomImage: UpdateRoomImageUseCase,
    private val roomRepository: RoomRepository,
) {
    @PostMapping
    suspend fun createNewRoom(
        @RequestUser user: LoginUser,
        @RequestBody body: CreateNewRoomRequest,
    ) = withUseCase(
        useCase = createNewRoom,
        param = CreateNewRoomUseCase.Input(
            roomName = body.roomName,
            owner = withUseCase(
                useCase = getUserById,
                param = user.userId,
            ),
            roomImageUrl = body.roomImageUrl,
        ),
    )

    @GetMapping("/{roomId}/users")
    suspend fun getUsers(
        @PathVariable roomId: String,
    ) = withUseCase(
        useCase = getRoomUsers,
        param = roomId,
        mappingFunction = { it.map(UserResponse::from) },
    )

    @PostMapping("/{roomId}/room-image")
    suspend fun updateRoomImage(
        @PathVariable roomId: String,
        @RequestBody dto: UpdateRoomImageRequest,
    ) = withUseCase(
        useCase = updateRoomImage,
        param = UpdateRoomImageUseCase.Input(
            room = roomRepository.getById(roomId),
            imageUrl = dto.imageUrl,
        ),
    )
}
