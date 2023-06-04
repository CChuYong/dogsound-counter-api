package co.bearus.dogsoundcounter.presenter

import co.bearus.dogsoundcounter.usecases.IdentityGenerator
import co.bearus.dogsoundcounter.usecases.user.CreateNewUserUseCase
import co.bearus.dogsoundcounter.usecases.user.GetUserByEmailUseCase
import co.bearus.dogsoundcounter.usecases.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class UseCaseImplementer {
    @Bean
    fun createNewUserUseCase(userRepository: UserRepository, identityGenerator: IdentityGenerator) = CreateNewUserUseCase(userRepository, identityGenerator)

    @Bean
    fun getUserByEmailUseCase(userRepository: UserRepository) = GetUserByEmailUseCase(userRepository)
}
