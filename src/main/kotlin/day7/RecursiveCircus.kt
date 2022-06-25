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

data class Program(val name: String, val weight: Int, val children: List<String>) {

    fun totalWeight(map: Map<String, Program>): Int {
        return if (children.isNullOrEmpty()) weight
        else weight +
                children.mapNotNull { map[it] }.map { it.totalWeight(map) }.sum()
    }

    companion object {
        fun from(name: String, input: List<String>): Program {
            val line = input.first { it.startsWith(name) }
            val weight = line.substringAfter("(").substringBefore(")").toInt()
            val children = line.substringAfter(" -> ", "").split(", ")
            return Program(name, weight, children)
        }
    }
}

fun parseTower(input: List<String>): List<Program> {
    val names = input.map { it.substringBefore(" ") }
    return names.map { Program.from(it, input) }
}

fun lunch(input: List<String>) {
    val tower = parseTower(input)
    val map = tower.associateBy { it.name }
    map.values.filter { it.children.isNotEmpty() }.forEach { program ->
        val weights = program.children.mapNotNull { map[it]?.totalWeight(map) }
        if (weights.toSet().size > 1) {
            println("--")
            println(program)
            println(weights)
        }
    }
    // now we know the name of the problematic program and the difference
    // let's just calculate it manually
    map["apjxafk"]?.let { println(it.weight - 8) }
}