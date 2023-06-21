package co.bearus.dogsoundcounter.entities.exception.user

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class FriendCannotBeMyselfException : DomainException(ErrorCode.FRIEND_CANNOT_BE_MYSELF)