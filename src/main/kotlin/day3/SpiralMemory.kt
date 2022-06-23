package day3

fun main() {
    val input = 368078
    //breakfast(input)
    lunch(input)
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

enum class Direction {
    NORTH,
    WEST,
    SOUTH,
    EAST
}

data class Cell(val x: Int, val y: Int, val value: Int, val direction: Direction)

fun Map<Pair<Int, Int>, Cell>.maxX() = this.keys.maxOf { it.first }
fun Map<Pair<Int, Int>, Cell>.maxY() = this.keys.maxOf { it.second }
fun Map<Pair<Int, Int>, Cell>.minX() = this.keys.minOf { it.first }
fun Map<Pair<Int, Int>, Cell>.minY() = this.keys.minOf { it.second }

fun Map<Pair<Int, Int>, Cell>.neighborsValue(cx: Int, cy: Int): Int {
    return (cx - 1..cx + 1).flatMap { x ->
        (cy - 1..cy + 1).mapNotNull { y ->
            this[Pair(x, y)]?.value
        }
    }.sum()
}

fun Map<Pair<Int, Int>, Cell>.nextCell(cell: Cell): Cell {

    when (cell.direction) {
        Direction.EAST -> {
            val v = this.neighborsValue(cell.x + 1, cell.y)
            return if (cell.x + 1 > this.maxX()) Cell(cell.x + 1, cell.y, v, Direction.NORTH)
            else Cell(cell.x + 1, cell.y, v, Direction.EAST)
        }
        Direction.NORTH -> {
            val v = this.neighborsValue(cell.x, cell.y + 1)
            return if (cell.y + 1 > this.maxY()) Cell(cell.x, cell.y + 1, v, Direction.WEST)
            else Cell(cell.x, cell.y + 1, v, Direction.NORTH)
        }
        Direction.WEST -> {
            val v = this.neighborsValue(cell.x - 1, cell.y)
            return if (cell.x - 1 < this.minX()) Cell(cell.x - 1, cell.y, v, Direction.SOUTH)
            else Cell(cell.x - 1, cell.y, v, Direction.WEST)
        }
        Direction.SOUTH -> {
            val v = this.neighborsValue(cell.x, cell.y - 1)
            return if (cell.y - 1 < this.minY()) Cell(cell.x, cell.y - 1, v, Direction.EAST)
            else Cell(cell.x, cell.y - 1, v, Direction.SOUTH)
        }
    }
}

fun lunch(target: Int) {
    val cells: MutableMap<Pair<Int, Int>, Cell> = mutableMapOf()
    val start = Cell(0, 0, 1, Direction.EAST)
    cells[Pair(0, 0)] = start
    var next = start.copy()
    while (next.value <= target) {
        next = cells.nextCell(next)
        cells[Pair(next.x, next.y)] = next
    }
    println(next)
}