package co.bearus.dogsoundcounter.usecases

interface ObjectSerializer {
    fun serialize(data: Any): String
}
