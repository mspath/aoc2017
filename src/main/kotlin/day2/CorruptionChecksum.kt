package day2

import java.io.File

fun main() {
    val input = File("data/day2/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: List<String>) {
    val checksum = input.sumOf { row ->
        val values = row.split("\t").map { it.toInt() }.sorted()
        values.max() - values.min()
    }
    println(checksum)
}

fun divisible(num: Int, denom: Int) = num % denom == 0

fun lunch(input: List<String>) {
    val checksum = input.sumOf { row ->
        val values = row.split("\t").map { it.toInt() }.sorted().reversed()
        val divisibles = (0 until values.size - 1).flatMap { num ->
            (num + 1 until values.size).mapNotNull { denom ->
                val numerator = values[num]
                val denominator = values[denom]
                if (divisible(numerator, denominator)) numerator / denominator else null
            }
        }.sum()
        divisibles
    }
    println(checksum)
}