package day4

import java.io.File

fun main() {
    val input = File("data/day4/input.txt").readLines()
    breakfast(input)
    lunch(input)
}

fun breakfast(passphrases: List<String>) {
    val valid = passphrases.filter { passphrase ->
        val list = passphrase.split(" ")
        // toSet conveniently does the job for us
        val set = list.toSet()
        list.size == set.size
    }
    println(valid.size)
}

fun lunch(passphrases: List<String>) {
    val valid = passphrases.filter { passphrase ->
        val list = passphrase.split(" ")
            .map { it.toCharArray().sorted().joinToString("") }
        val set = list.toSet()
        list.size == set.size
    }
    println(valid.size)
}