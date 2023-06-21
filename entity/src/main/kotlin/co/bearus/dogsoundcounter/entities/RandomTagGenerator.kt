package co.bearus.dogsoundcounter.entities

object RandomTagGenerator {
    private fun pickNumber(): Int {
        return (1..9).random()
    }

    fun generate() = "${pickNumber()}${pickNumber()}${pickNumber()}${pickNumber()}"
}
