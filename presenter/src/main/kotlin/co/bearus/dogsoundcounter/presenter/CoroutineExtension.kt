package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <F, T, R> withUseCase(useCase: UseCase<F, T>, param: F, mappingFunction: (T) -> R): R {
    withContext(Dispatchers.Default) {
        useCase.execute(param)
    }.let { result ->
        return mappingFunction(result)
    }
}

suspend fun <F, T> withUseCase(useCase: UseCase<F, T>, param: F): T {
    return withContext(Dispatchers.Default) {
        useCase.execute(param)
    }
}

