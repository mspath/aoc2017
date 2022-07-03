package day20

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("data/day20/input.txt").readLines()
    breakfast(input)
}

data class Particle(var position: Triple<Int, Int, Int>,
                    var velocity: Triple<Int, Int, Int>,
                    val acceleration: Triple<Int, Int, Int>) {

    val md = abs(position.first) + abs(position.second) + abs(position.third)

    val mdAcc = abs(acceleration.first) + abs(acceleration.second) + abs(acceleration.third)

    fun tick() {
        velocity = Triple(velocity.first + acceleration.first,
            velocity.second + acceleration.second,
            velocity.third + acceleration.third)
        position = Triple(position.first + velocity.first,
            position.second + velocity.second,
            position.third + velocity.third)
    }

    companion object {
        fun parse(p: String): Particle {
            val parts = p.split(", ")
                .map { it.substringAfter("<").substringBefore('>').split(",").map { it.toInt() } }
            return Particle(
                Triple(parts[0][0], parts[0][1], parts[0][2]),
                Triple(parts[1][0], parts[1][1], parts[1][2]),
                Triple(parts[2][0], parts[2][1], parts[2][2])
            )
        }
    }
}

// brut force didn't work, but picking the particle with min acceleration got the job done
fun breakfast(input: List<String>) {
    val particles = input.map { Particle.parse(it) }
    val minAcc = particles.sortedBy { it.mdAcc }.first()
    val index = particles.indexOf(minAcc)
    println(index)
}