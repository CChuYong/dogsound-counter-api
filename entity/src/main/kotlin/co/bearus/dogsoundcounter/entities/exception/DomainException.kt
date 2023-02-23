package co.bearus.dogsoundcounter.entities.exception

open class DomainException(val errorCode: ErrorCode) : RuntimeException("${errorCode.code}: ${errorCode.message}")