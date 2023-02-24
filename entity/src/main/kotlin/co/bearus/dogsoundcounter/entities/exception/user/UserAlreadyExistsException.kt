package co.bearus.dogsoundcounter.entities.exception.user

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class UserAlreadyExistsException : DomainException(ErrorCode.USER_ALREADY_EXISTS)