package day5

import java.io.File

fun main() {
    val input = File("data/day5/input.txt").readLines().map { it.toInt() }.toMutableList()
    breakfast(input)
    lunch(input)
}

fun breakfast(jumps: MutableList<Int>) {
    var counter = 0
    var index = 0
    var range = jumps.indices
    while (true) {
        counter++
        val step = jumps[index]
        val next = index + step
        if (next !in range) break
        jumps[index]++
        index += step
    }
    println(counter)
}

fun lunch(jumps: MutableList<Int>) {
    var counter = 0
    var index = 0
    var range = jumps.indices
    while (true) {
        counter++
        val step = jumps[index]
        val next = index + step
        if (next !in range) break
        if (jumps[index] >= 3) jumps[index]-- else jumps[index]++
        index += step
    }
    println(counter)
}