package day7

import java.io.File

fun main() {
    val input = File("data/day7/input.txt").readLines()
    val root = breakfast(input)
    println(root)
    lunch(input)
}

// we just have to subtract the set of nodes which have parents from the set of all nodes
fun breakfast(input: List<String>): String {
    val names = input.map { it.substringBefore(" ") }.toSet()
    val children = input.flatMap { it.substringAfter(" -> ").split(", ") }.toSet()
    val root = names.subtract(children)
    return root.first()
}

fun children(input: List<String>, node: String): List<String> {
    return input.filter { it.startsWith(node) }
        .first()
        .substringAfter(" -> ")
        .split(", ")
}

fun lunch(input: List<String>) {
    val root = breakfast(input)
    println(root)
    println(children(input, root))
}