package co.bearus.dogsoundcounter.entities.exception.user

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class FriendRequestNotValidException : DomainException(ErrorCode.FRIEND_REQUEST_NOT_VALID)