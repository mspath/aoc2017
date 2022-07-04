package day22

import java.io.File

typealias Grid = MutableSet<Pair<Int, Int>>

typealias GridEvolved = MutableSet<Node>

fun main() {
    val input = File("data/day22/sample.txt").readLines()
    //breakfast(input)
    lunch(input)
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
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }

    fun turnLeft() {
        direction = when (direction) {
            Direction.UP -> Direction.LEFT
            Direction.RIGHT -> Direction.UP
            Direction.DOWN -> Direction.RIGHT
            Direction.LEFT -> Direction.DOWN
        }
    }

    fun reverse() {
        direction = when (direction) {
            Direction.UP -> Direction.DOWN
            Direction.RIGHT -> Direction.LEFT
            Direction.DOWN -> Direction.UP
            Direction.LEFT -> Direction.RIGHT
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

enum class State {
    CLEAN,
    WEAKENED,
    INFECTED,
    FLAGGED
}

data class Node(val x: Int, val y: Int, var state: State)

fun parseGridEvolved(input: List<String>): GridEvolved {
    val grid: MutableSet<Node> = mutableSetOf()
    val size = input.size
    val depth = size / 2
    input.forEachIndexed { indexY, line ->
        val y = indexY - depth
        line.forEachIndexed { indexX, c ->
            val x = indexX - depth
            if (c == '#') grid.add(Node(x, y, State.INFECTED))
        }
    }
    return grid
}

fun GridEvolved.displayEvolved() {
    val minX = this.minOf { it.x } - 1
    val maxX = this.maxOf { it.x } + 1
    val minY = this.minOf { it.y } - 1
    val maxY = this.maxOf { it.y } + 1

    (minY..maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            val clean = this.filter { it.x == x && it.y == y }.isEmpty()
            if (clean) {
                print('.')
            } else {
                val node = this.first { it.x == x && it.y == y }
                when (node.state) {
                    State.WEAKENED -> {
                        print('W')
                    }
                    State.INFECTED -> {
                        print('#')
                    }
                    State.FLAGGED -> {
                        print('F')
                    }
                    else -> {}
                }
            }
        }
        print('\n')
    }
}

fun GridEvolved.burstEvolved(virus: Virus) {
    val clean = this.filter { it.x == virus.position.first && it.y == virus.position.second }.isEmpty()
    if (clean) {
        virus.turnLeft()
        this.add(Node(virus.position.first, virus.position.second, State.WEAKENED))
    }
    else {
        val node = this.first { it.x == virus.position.first && it.y == virus.position.second }
        when (node.state) {
            State.WEAKENED -> {
                node.state = State.INFECTED
                virus.counter++
            }
            State.INFECTED -> {
                node.state = State.FLAGGED
                virus.turnRight()
            }
            State.FLAGGED -> {
                node.state = State.CLEAN
                this.remove(node)
                virus.reverse()
            }
            else -> {}
        }
    }
    virus.move()
}

// TODO unfinished. prob. an off by one error
fun lunch(input: List<String>) {
    val grid = parseGridEvolved(input)
    val virus = Virus()
    repeat(12) {
        grid.burstEvolved(virus)
        println(it)
        grid.displayEvolved()
        println("---")
    }
    grid.displayEvolved()
    println(virus.counter)
}