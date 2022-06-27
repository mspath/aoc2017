package day9

import java.io.File

fun main() {
    val input = File("data/day9/input.txt").readText()
    breakfast(input)
    lunch(input)
}

fun breakfast(input: String) {
    val canceled = "!.".toRegex()
    val inputCleaner = input.replace(canceled, "")
    val garbage = "<.*?>".toRegex()
    val inputCleanest = inputCleaner.replace(garbage, "")
    val misc = "[^{}]".toRegex()
    val rest = inputCleanest.replace(misc, "")
    var depth = 0
    var score = 0
    rest.forEach { c ->
        when (c) {
            '{' -> depth++
            '}' -> score += depth--
        }
    }
    println(score)
}

// we just have to parse the garbage after removing the canceled chars
fun lunch(input: String) {
    val canceled = "!.".toRegex()
    val inputCleaner = input.replace(canceled, "")
    val garbage = "<.*?>".toRegex()
    val garbageMatches = garbage.findAll(inputCleaner)
    val result = garbageMatches.map { it.value.drop(1).dropLast(1) }
        .joinToString("")
    println(result.length)
}