package day13

import java.io.File

fun main() {
    val input = File("data/day13/input.txt").readLines().map { line ->
        val tokens = line.split(": ")
        tokens.first().toInt() to tokens.last().toInt()
    }.toMap()
    breakfast(input)
}

fun breakfast(input: Map<Int, Int>) {
    val max = input.keys.max()
    val result = (0 .. max).sumOf { step ->
        val scanner = input.getOrDefault(step, 0)
        if (scanner == 0) 0
        else {
            if (step % ((scanner - 1) * 2) == 0) step * scanner else 0
        }
    }
    println(result)
}