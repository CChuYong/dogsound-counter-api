package co.bearus.dogsoundcounter.infrastructure

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Component

@Component
class ULIDGenerator: IdentityGenerator {
    override fun createIdentity(): String {
        return UlidCreator.getMonotonicUlid().toString()
    }
}
