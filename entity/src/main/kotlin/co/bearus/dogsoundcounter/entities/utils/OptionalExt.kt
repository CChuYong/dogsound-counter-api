package co.bearus.dogsoundcounter.entities.utils

fun<T> T?.throwIfNotNull(ex: Exception){
    if(this != null) throw ex
}