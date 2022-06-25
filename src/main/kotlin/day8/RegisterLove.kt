package day8

import java.io.File

typealias Registry = MutableMap<String, Int>

data class Instruction(val register: String, val operation: String, val value: Int,
                       val dependency: String, val condition: (Int) -> Boolean) {

    companion object {
        fun buildCondition(operation: String, amount: Int): (Int) -> Boolean =
            when (operation) {
                "==" -> { value -> value == amount }
                "!=" -> { value -> value != amount }
                "<" -> { value -> value < amount }
                ">" -> { value -> value > amount }
                "<=" -> { value -> value <= amount }
                ">=" -> { value -> value >= amount }
                else -> throw IllegalArgumentException("unknown operation")
            }
    }
}

fun main() {
    val input = File("data/day8/input.txt").readLines().map {
        val tokens = it.split(" ")
        Instruction(tokens[0], tokens[1], tokens[2].toInt(), tokens[4],
            Instruction.buildCondition(tokens[5], tokens[6].toInt()))
    }
    breakfast(input)
    lunch(input)
}

fun Registry.process(instruction: Instruction) {
    val value = this.getOrDefault(instruction.dependency, 0)
    if (instruction.condition(value)) {
        when (instruction.operation) {
            "inc" -> this[instruction.register] = this.getOrDefault(instruction.register, 0) +
                    instruction.value
            "dec" -> this[instruction.register] = this.getOrDefault(instruction.register, 0) -
                    instruction.value
            else -> throw IllegalArgumentException("unknown operation")
        }
    }
}

fun breakfast(instructions: List<Instruction>) {
    val names = instructions.map { it.register }.toSet().sorted()
    val registers = names.associateWith { 0 }.toMutableMap()
    instructions.forEach {
        registers.process(it)
    }
    println(registers.values.max())
}

fun lunch(instructions: List<Instruction>) {
    var max = Int.MIN_VALUE
    val names = instructions.map { it.register }.toSet().sorted()
    val registers = names.associateWith { 0 }.toMutableMap()
    instructions.forEach {
        registers.process(it)
        val loopMax = registers.values.max()
        if (loopMax > max) max = loopMax
    }
    println(max)
}