package day15

import javax.crypto.KeyGenerator

fun main() {
    val generatorAStart =  512
    val generatorBStart = 191
    breakfast(generatorAStart, generatorBStart, 40_000_000)
    lunch(generatorAStart, generatorBStart, 5_000_000)
}

fun breakfast(generatorAStart: Int, generatorBStart: Int, cycles: Int) {
    val factorA = 16807L
    val factorB = 48271L
    var currentA = generatorAStart
    var currentB = generatorBStart
    var count = 0
    repeat(cycles) {
        currentA = (factorA * currentA % Int.MAX_VALUE).toInt()
        currentB = (factorB * currentB % Int.MAX_VALUE).toInt()
        if (currentA.toShort() == currentB.toShort()) count++
    }
    println(count)
}

fun generator(start: Long, factor: Long): Sequence<Short> =
    generateSequence((start * factor) % Int.MAX_VALUE) { previous ->
        (previous * factor) % Int.MAX_VALUE
    }.map { it.toShort() }

fun lunch(generatorAStart: Int, generatorBStart: Int, cycles: Int) {
    val factorA = 16807L
    val factorB = 48271L
    val generatorA = generator(generatorAStart.toLong(), factorA)
    val generatorB = generator(generatorBStart.toLong(), factorB)

    val count = generatorA.filter { it % 4 == 0 }
        .zip(generatorB.filter { it % 8 == 0 })
        .take(cycles)
        .count { it.first == it.second }

    println(count)
}