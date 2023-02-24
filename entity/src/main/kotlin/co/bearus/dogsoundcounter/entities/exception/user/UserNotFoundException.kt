package co.bearus.dogsoundcounter.entities.exception.user

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class UserNotFoundException : DomainException(ErrorCode.USER_NOT_FOUND)