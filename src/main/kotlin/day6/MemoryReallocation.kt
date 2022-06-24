package day6

import java.io.File

fun main() {
    val input = File("data/day6/input.txt").readText().split("\t").map { it.toInt() }
    breakfast(input.toMutableList())
    lunch(input.toMutableList())
}

fun reallocate(banks: MutableList<Int>) {
    val max = banks.max()
    val indexMax = banks.indexOfFirst { it == max }
    banks[indexMax] = 0
    repeat(max) {
        banks[(indexMax + it + 1) % banks.size]++
    }
}

fun breakfast(banks: MutableList<Int>) {

    val hashes: MutableSet<Int> = mutableSetOf()
    hashes.add(banks.hashCode())

    repeat(Int.MAX_VALUE) { counter ->
        reallocate(banks)
        val hash = banks.hashCode()
        if (hashes.contains(hash)) {
            println(counter + 1)
            return
        }
        hashes.add(hash)
    }
}

// we just need to use a list instead of a set. then we can look up the index of the hash
// and calculate the size of the loop on first duplicate
fun lunch(banks: MutableList<Int>) {

    val hashes: MutableList<Int> = mutableListOf()
    hashes.add(banks.hashCode())

    repeat(Int.MAX_VALUE) { counter ->
        reallocate(banks)
        val hash = banks.hashCode()
        if (hashes.contains(hash)) {
            val start = hashes.indexOfFirst { it == hash }
            println(counter + 1 - start)
            return
        }
        hashes.add(hash)
    }
}