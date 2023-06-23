package co.bearus.dogsoundcounter.entities.exception.user

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class AlreadyFriendException : DomainException(ErrorCode.ALREADY_FRIEND)