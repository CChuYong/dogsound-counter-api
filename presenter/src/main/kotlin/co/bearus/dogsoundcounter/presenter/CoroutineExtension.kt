package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

suspend fun <F, T, R> withUseCase(useCase: UseCase<F, T>, param: F, mappingFunction: suspend (T) -> R): R {
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

suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

suspend fun <A, B> Iterable<A>.parallelMapLimited(concurrency: Int, f: suspend (A) -> B): List<B> = coroutineScope {
    chunked(concurrency).map {
        map { async { f(it) } }.awaitAll()
    }.flatten()
}
