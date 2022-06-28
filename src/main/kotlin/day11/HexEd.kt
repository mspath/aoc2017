package day11

import java.io.File
import java.lang.Math.abs


fun main() {
    val input = File("data/day11/input.txt").readText().split(",")
    breakfast(input)
}

fun breakfast(path: List<String>) {
    val counts = path.groupingBy { it }.eachCount()
    println(counts)
    val easts = counts.getOrDefault("se", 0) +
            counts.getOrDefault("ne", 0)
    val wests = counts.getOrDefault("sw", 0) +
            counts.getOrDefault("nw", 0)
    val norths = counts.getOrDefault("n", 0) * 2 +
            counts.getOrDefault("ne", 0) +
            counts.getOrDefault("nw", 0)
    val souths = counts.getOrDefault("s", 0) * 2 +
            counts.getOrDefault("se", 0) +
            counts.getOrDefault("sw", 0)

    val x = kotlin.math.abs(easts - wests)
    val y = kotlin.math.abs(norths - souths)

    val result = x + (y - x) / 2
    println(result)
}