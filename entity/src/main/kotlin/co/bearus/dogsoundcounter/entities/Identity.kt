package co.bearus.dogsoundcounter.entities

data class Identity(val id: Long) {
    fun isNothing(): Boolean {
        return this == NOTHING
    }

    companion object {
        val NOTHING = of(-Long.MAX_VALUE);
        fun of(id: Long): Identity {
            return Identity(id)
        }
    }
}
