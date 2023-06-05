package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Room
import co.bearus.dogsoundcounter.entities.User
import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.UseCase

class CreateNewViolentUseCase(
    private val identityGenerator: IdentityGenerator,
    private val violentRepository: ViolentRepository,
): UseCase<CreateNewViolentUseCase.Input, Violent> {
    data class Input(
        val room: Room,
        val name: String,
        val description: String,
        val price: Int,
        val createUser: User,
    )

    override suspend fun execute(input: CreateNewViolentUseCase.Input): Violent {
        val newViolent = Violent.newInstance(
            violentId = identityGenerator.createIdentity(),
            roomId = input.room.roomId,
            violentPrice = input.price,
            description = input.description,
            name = input.name,
            createdUserId = input.createUser.userId,
        )
        return violentRepository.persist(newViolent)
    }
}