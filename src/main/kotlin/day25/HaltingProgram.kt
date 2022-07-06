package day25

import java.io.File

fun main() {
    val input = File("data/day25/input.txt").readText()
    breakfast(input)
}

data class State(var state: String, var cursor: Int)

data class Rule(val name: String,
                val w0: Int, val m0: String, val s0: String,
                val w1: Int, val m1: String, val s1: String)

fun Rule.step(tape: MutableList<Int>, state: State) {
    val value = tape[state.cursor]
    when (value) {
        0 -> {
            tape[state.cursor] = this.w0
            if (this.m0 == "right") {
                state.cursor++
                // increase tape
                if (state.cursor == tape.size) tape.add(0)
            }
            else {
                state.cursor--
                // increase tape
                if (state.cursor == -1) {
                    tape.add(0, 0)
                    state.cursor++
                }
            }
            state.state = s0
        }
        1 -> {
            tape[state.cursor] = this.w1
            if (this.m1 == "right") {
                state.cursor++
                if (state.cursor == tape.size) tape.add(0)
            }
            else {
                state.cursor--
                if (state.cursor == -1) {
                    tape.add(0, 0)
                    state.cursor++
                }
            }
            state.state = s1
        }
    }
}

fun breakfast(input: String) {
    val tokens = input.split("\n\n")
    val start = tokens[0]
    val rest = tokens.drop(1)
    val initialState = start.substringAfter("state ").substringBefore(".")
    val state = State(initialState, 0)
    val steps = start.substringAfter("after ").substringBefore(" steps").toInt()
    val rules = rest.map {
        val tokens = it.split("\n")
        val state = tokens[0].substringAfter("state ").substringBefore(":")
        println(state)
        val w0 = tokens[2].substringAfter("value ").substringBefore(".").toInt()
        val m0 = tokens[3].substringAfter("the ").substringBefore(".")
        val s0 = tokens[4].substringAfter("state ").substringBefore(".")
        val w1 = tokens[6].substringAfter("value ").substringBefore(".").toInt()
        val m1 = tokens[7].substringAfter("the ").substringBefore(".")
        val s1 = tokens[8].substringAfter("state ").substringBefore(".")
        Rule(state, w0, m0, s0, w1, m1, s1)
    }
    val tape: MutableList<Int> = mutableListOf(0)
    repeat(steps) {
        val rule = rules.first { it.name == state.state }
        rule.step(tape, state)
    }
    println(tape)
    val checksum = tape.sum()
    println(checksum)
}