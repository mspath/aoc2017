package day16

import java.io.File


fun main() {
    val input = File("data/day16/input.txt").readText()
    val resultBreakfast = breakfast(input)
    println(resultBreakfast)
    lunch(input)
}

data class Move(val type: Char, val first: Int, val second: Int = 0)

fun Move.process(state: String): String {
    return when (this.type) {
        's' -> {
            state.substring(state.length - this.first) + state.substring(0, state.length - this.first)
        }
        'x' -> {
            val chars = state.toCharArray()
            chars[this.first] = chars[this.second].also { chars[this.second] = chars[this.first] }
            String(chars)
        }
        'p' -> {
            val a = 'a' + this.first
            val b = 'a' + this.second
            var next = state.replace(a, 'x')
            next = next.replace(b, 'y')
            next = next.replace('x', b)
            next.replace('y', a)
        }
        else -> state
    }
}

fun breakfast(input: String): String {
    val dance = input.split(",").mapNotNull {
        val type = it[0]
        val args = it.substring(1)
        when (type) {
            's' -> Move(type, args.toInt())
            'x' -> {
                val params = args.split("/")
                Move(type, params.first().toInt(), params.last().toInt())
            }
            'p' -> {
                Move(type, args[0] - 'a', args[2] - 'a')
            }
            else -> null
        }
    }

    var state = "abcdefghijklmnop"
    dance.forEach {
        state = it.process(state)
    }

    return state
}

fun next(dance: List<Move>, state: String): String {
    var next = state
    dance.forEach {
        next = it.process(next)
    }
    return next
}

fun lunch(input: String) {
    val dance = input.split(",").mapNotNull {
        val type = it[0]
        val args = it.substring(1)
        when (type) {
            's' -> Move(type, args.toInt())
            'x' -> {
                val params = args.split("/")
                Move(type, params.first().toInt(), params.last().toInt())
            }
            'p' -> {
                Move(type, args[0] - 'a', args[2] - 'a')
            }
            else -> null
        }
    }

    val set: MutableSet<String> = mutableSetOf()
    var state = "abcdefghijklmnop"
    set.add(state)
    repeat(43) {
        println(state)
        state = next(dance, state)
        println(it)
        set.add(state)
    }

    // we see that after 42 dances the cycle repeats
    // the rest is 34 -> we can look up the 34th element in the print
    println(1_000_000_000 % 42)
}