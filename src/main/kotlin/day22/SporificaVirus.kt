package day22

import java.io.File

typealias Grid = MutableSet<Pair<Int, Int>>

fun main() {
    val input = File("data/day22/input.txt").readLines()
    breakfast(input)
}

fun parseGrid(input: List<String>): Grid {
    val grid: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val size = input.size
    val depth = size / 2
    input.forEachIndexed { indexY, line ->
        val y = indexY - depth
        line.forEachIndexed { indexX, c ->
            val x = indexX - depth
            if (c == '#') grid.add(Pair(x, y))
        }
    }
    return grid
}

fun Grid.burst(virus: Virus) {
    val infected = this.contains(virus.position)
    if (infected) {
        virus.turnRight()
        this.remove(virus.position)
    }
    else {
        virus.turnLeft()
        this.add(virus.position)
        virus.counter++
    }
    virus.move()
}

fun Grid.display() {
    val minX = this.minOf { it.first }
    val maxX = this.maxOf { it.first }
    val minY = this.minOf { it.second }
    val maxY = this.maxOf { it.second }

    (minY..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            if (this.contains(Pair(x, y))) print('#') else print('.')
        }
        print('\n')
    }
}

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

data class Virus(var position: Pair<Int, Int> = Pair(0, 0),
                 var direction: Direction = Direction.UP,
                 var counter: Int = 0) {

    fun turnRight() {
        when (direction) {
            Direction.UP -> direction = Direction.RIGHT
            Direction.RIGHT -> direction = Direction.DOWN
            Direction.DOWN -> direction = Direction.LEFT
            Direction.LEFT -> direction = Direction.UP
        }
    }

    fun turnLeft() {
        when (direction) {
            Direction.UP -> direction = Direction.LEFT
            Direction.RIGHT -> direction = Direction.UP
            Direction.DOWN -> direction = Direction.RIGHT
            Direction.LEFT -> direction = Direction.DOWN
        }
    }

    fun move() {
        when (direction) {
            Direction.UP -> position = Pair(position.first, position.second - 1)
            Direction.RIGHT -> position = Pair(position.first + 1, position.second)
            Direction.DOWN -> position = Pair(position.first, position.second + 1)
            Direction.LEFT -> position = Pair(position.first - 1, position.second)
        }
    }
}

fun breakfast(input: List<String>) {
    val grid = parseGrid(input)
    val virus = Virus()
    repeat(10_000) {
        grid.burst(virus)
    }
    grid.display()
    println(virus.counter)
}