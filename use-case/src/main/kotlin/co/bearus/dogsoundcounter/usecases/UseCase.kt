package co.bearus.dogsoundcounter.usecases

interface UseCase<F, T> {
    suspend fun execute(input: F): T
}