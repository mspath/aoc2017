package day12

import java.io.File

fun main() {
    val input = File("data/day12/input.txt").readLines()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    val resultLunch = lunch(input)
    println(resultLunch)
}

fun getConnections(map: Map<Int, Set<Int>>, from: Int, visited: MutableSet<Int> = mutableSetOf()): Set<Int> {
    if(from !in visited) {
        visited.add(from)
        map.getValue(from).forEach { getConnections(map, it, visited) }
    }
    return visited
}

fun breakfast(pipes: List<String>): Int {
    val map = pipes.map {
        val line = it.split(" <-> ")
        line.first().toInt() to line.last().split(", ").map { n -> n.toInt() }.toSet()
    }.toMap()
    return getConnections(map, 0).size
}

fun lunch(pipes: List<String>): Int {
    var map = pipes.map {
        val line = it.split(" <-> ")
        line.first().toInt() to line.last().split(", ").map { n -> n.toInt() }.toSet()
    }.toMap()
    var areas = 0
    while (map.keys.isNotEmpty()) {
        areas++
        val pool = getConnections(map, map.keys.first())
        map = map.filter { it.key !in pool }
    }
    return areas
}