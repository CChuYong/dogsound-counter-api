package co.bearus.dogsoundcounter.usecases.violent

import co.bearus.dogsoundcounter.entities.Violent
import co.bearus.dogsoundcounter.usecases.UseCase

class DeleteViolentUseCase(
    private val violentRepository: ViolentRepository,
): UseCase<Violent, Unit> {
    override suspend fun execute(input: Violent) {
        violentRepository.deleteByViolentId(input.violentId)
    }
}