package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.UseCase

class GetViolentByIdUseCase(
    private val violentRepository: ViolentRepository,
) : UseCase<String, Violent> {
    override suspend fun execute(input: String): Violent {
        return violentRepository.getById(input)
    }
}
