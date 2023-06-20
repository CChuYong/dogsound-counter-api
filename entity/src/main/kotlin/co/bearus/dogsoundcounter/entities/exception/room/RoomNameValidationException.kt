package co.bearus.dogsoundcounter.entities.exception.room

import co.bearus.dogsoundcounter.entities.exception.DomainException
import co.bearus.dogsoundcounter.entities.exception.ErrorCode

class RoomNameValidationException : DomainException(ErrorCode.ROOM_NAME_VALIDATION)