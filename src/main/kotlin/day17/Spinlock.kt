package day17

fun main() {
    val steps = 316
    val cyclesBreakfast = 2017
    val cycles = 50_000_000
    breakfast(steps, cyclesBreakfast)
    lunch(steps, cycles)
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

// brute force won't word,
// but we just need to keep up with the index of 0 and which number is behind
// - index of 0 shifts by one every time a value is inserted before it
// - the number behind gets updated every time a value is inserted at index0 + 1
fun lunch(steps: Int, cycles: Int) {
    var position = 0
    var index0 = 0
    var following0 = Int.MIN_VALUE
    var size = 1
    repeat(cycles) { next ->
        val insertPosition = (position + steps) % size
        val diff0 = index0 - insertPosition
        when {
            diff0 < index0 -> {
                index0++
            }
            diff0 == index0 -> {
                following0 = next
            }
        }
        size++
        position = insertPosition + 1
    }
    println(following0)
}