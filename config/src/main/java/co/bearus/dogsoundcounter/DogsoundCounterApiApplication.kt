package co.bearus.dogsoundcounterapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DogsoundCounterApiApplication

fun main(args: Array<String>) {
	runApplication<DogsoundCounterApiApplication>(*args)
}
