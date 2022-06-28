package day11

import java.io.File


fun main() {
    val input = File("data/day11/input.txt").readText().split(",")
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    val resultLunch = lunch(input)
    println(resultLunch)
}

fun breakfast(path: List<String>): Int {
    val counts = path.groupingBy { it }.eachCount()
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

    // we don't need to worry about the specific direction, just vertical and horizontal movement
    val x = kotlin.math.abs(easts - wests)
    val y = kotlin.math.abs(norths - souths)
    return x + (y - x) / 2
}

fun lunch(path: List<String>): Int {
    var max = Int.MIN_VALUE
    var rest = path
    // the dataset is small enough for brute force
    while (rest.size > 0) {
        rest = rest.dropLast(1)
        val steps = breakfast(rest)
        if (steps > max) max = steps
    }
    return max
}