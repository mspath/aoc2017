package day12

import java.io.File


fun main() {
    val input = File("data/day12/input.txt").readLines()
    breakfast(input)
}

fun getConnections(map: Map<Int, Set<Int>>, from: Int, visited: MutableSet<Int> = mutableSetOf()): Set<Int> {
    if(from !in visited) {
        visited.add(from)
        map.getValue(from).forEach { getConnections(map, it, visited) }
    }
    return visited
}

fun breakfast(pipes: List<String>) {
    val map = pipes.map {
        val line = it.split(" <-> ")
        line.first().toInt() to line.last().split(", ").map { n -> n.toInt() }.toSet()
    }.toMap()
    val result = getConnections(map, 0).size
    println(result)
}