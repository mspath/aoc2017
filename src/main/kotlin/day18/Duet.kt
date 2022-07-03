package day18

import java.io.File


fun main() {
    val input = File("data/day18/input.txt").readLines().map { it.substring(0, 3) to it.substring(4) }
    breakfast(input)
}

// extension function to look up values either from the register or as parsed value
fun MutableMap<Char, Int>.lookup(n: String) =
    if (n[0] in 'a'..'z') this.getOrDefault(n[0], 0) else n.toInt()


fun breakfast(input: List<Pair<String, String>>) {
    var index = 0
    val registers: MutableMap<Char, Int> = mutableMapOf()
    var rcv = Int.MIN_VALUE
    while (true) {
        val action = input[index]
        when (action.first) {
            "set" -> {
                val register = action.second.split(" ").first()[0]
                val value = registers.lookup(action.second.split(" ").last())
                registers[register] = value
                index++
            }
            "snd" -> {
                val register = action.second[0]
                val value = registers.getOrDefault(register, 0)
                println("snd: $value")
                rcv = value
                index++
            }
            "rcv" -> {
                val register = action.second[0]
                val value = registers.getOrDefault(register, 0)
                if (value > 0) {
                    println("rcv: $rcv")
                    break
                }
                index++
            }
            "add" -> {
                val register = action.second.split(" ").first()[0]
                val value = registers.lookup(action.second.split(" ").last())
                registers[register] = registers.getOrDefault(register, 0) + value
                index++
            }
            "mul" -> {
                val register = action.second.split(" ").first()[0]
                val value = registers.lookup(action.second.split(" ").last())
                registers[register] = registers.getOrDefault(register, 0) * value
                index++
            }
            "mod" -> {
                val register = action.second.split(" ").first()[0]
                val value = registers.lookup(action.second.split(" ").last())
                registers[register] = registers.getOrDefault(register, 0) % value
                index++
            }
            "jgz" -> {
                val trigger = registers.lookup(action.second.split(" ").first()) > 0
                val value = registers.lookup(action.second.split(" ").last())
                if (trigger) {
                    index += value
                } else index++
            }
            else -> index++
        }
        if (index > input.lastIndex) break
    }
}