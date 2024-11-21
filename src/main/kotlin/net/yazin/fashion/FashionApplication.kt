package net.yazin.fashion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FashionApplication

fun main(args: Array<String>) {
	runApplication<FashionApplication>(*args)
}
