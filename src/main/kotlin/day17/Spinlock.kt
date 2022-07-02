package day17


fun main() {
    val stepsSample = 3
    val steps = 316
    val cyclesSample = 9
    val cycles = 2017
    breakfast(steps, cycles)
}

fun breakfast(steps: Int, cycles: Int) {
    val spinlock: MutableList<Int> = mutableListOf(0)
    var position = 0
    repeat(cycles) {
        position = (position + steps) % spinlock.size
        spinlock.add(++position, it + 1)
    }
    println(spinlock[position + 1])
}