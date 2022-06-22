package day3

fun main() {
    val input = 368078
    breakfast(input)
}

fun depth(cell: Int): Int {
    // 0, 1,   2,   3,   4,   5, ..
    // 1, 3*3, 5*5, 7*7, 9*9, 11*11, ..
    var level = 0
    if (cell == 1) return level

    while (true) {
        level++
        val max = (level * 2 + 1) * (level * 2 + 1)
        if (cell <= max) return level
    }
}

fun getDistance(cell: Int): Int {
    // 1. get depth and max
    val depth = depth(cell)
    val max = (depth * 2 + 1) * (depth * 2 + 1)
    var position = max
    var distance = depth * 2
    if (cell == max) return distance

    // just walk along the circle to find the value
    repeat(4) {
        repeat(depth) {
            position--
            distance--
            if (position == cell) return distance
        }
        repeat(depth) {
            position--
            distance++
            if (position == cell) return distance
        }
    }
    // should not happen
    return -1
}

fun breakfast(cell: Int) {
    val distance = getDistance(cell)
    println(distance)
}