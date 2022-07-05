package day24

import java.io.File


fun main() {
    val input = File("data/day24/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

data class Component(val x: Int, val y: Int) {
    val strength = x + y
    fun connectable(port: Int) = x == port || y == port
    fun other(port: Int) = if (x == port) y else x
}

fun List<Component>.strength() = this.sumOf { it.strength }

fun buildBridges(bridge: List<Component> = emptyList(),
                 components: List<Component>,
                 port: Int): List<List<Component>> {

    val available = components.filter { it.connectable(port) }
    if (available.isEmpty()) return listOf(bridge)

    return available.flatMap { component ->
        buildBridges(
            bridge + component,
            components - component,
            component.other(port)
        )
    }
}

fun breakfast(input: List<String>) {
    val components = input.map {
        val pins = it.split("/")
        Component(pins.first().toInt(), pins.last().toInt())
    }
    val bridges = buildBridges(components = components, port = 0)
    val strongest = bridges.maxBy { it.strength() }
    println(strongest)
    println(strongest.strength())
}

fun lunch(input: List<String>) {
    val components = input.map {
        val pins = it.split("/")
        Component(pins.first().toInt(), pins.last().toInt())
    }
    val bridges = buildBridges(components = components, port = 0)
    val longest = bridges.maxBy { it.size }
    println(longest)
    println(longest.strength())
}