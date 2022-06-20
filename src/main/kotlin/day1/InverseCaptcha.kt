package day1

import java.io.File


fun main() {
    val input = File("data/day1/input.txt").readText()

    // we append the first char to account for being circular
    breakfast(input + input.first())

    lunch(input)
}

fun breakfast(input: String) {
    val result = input.windowed(2)
        .filter { it.first() == it.last() }
        .map { it.first().toString().toInt() }
        .sum()
    println(result)
}

fun lunch(input: String) {
    val part1 = input.substring(0, input.length / 2)
    val part2 = input.substring(input.length / 2)

    // since the matching will mirror each other we just need to split in half and zip
    // and then multiply with 2
    val resultHalf = part1.zip(part2)
        .filter { it.first == it.second }
        .map { it.first.toString().toInt() }
        .sum()
    println(resultHalf * 2)
}