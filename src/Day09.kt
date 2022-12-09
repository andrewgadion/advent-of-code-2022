import kotlin.math.*

data class Pos(val x: Int, val y: Int) {
    fun distance(p: Pos) = max(abs(x - p.x), abs(y - p.y))
}
fun main() {
    fun move(rope: List<Pos>, command: String) =
        sequence {
            val (direction, steps) = command.split(" ")
            var lastRope = rope
            repeat(steps.toInt()) {
                val newHead = when (direction) {
                    "U" -> Pos(lastRope[0].x, lastRope[0].y + 1)
                    "D" -> Pos(lastRope[0].x, lastRope[0].y - 1)
                    "R" -> Pos(lastRope[0].x + 1, lastRope[0].y)
                    "L" -> Pos(lastRope[0].x - 1, lastRope[0].y)
                    else -> throw Exception("unknown command")
                }
                lastRope = lastRope.drop(1).fold(listOf(newHead)) { acc, curKnot ->
                    acc + if (acc.last().distance(curKnot) > 1) {
                        Pos(curKnot.x + (acc.last().x - curKnot.x).coerceIn(-1, 1),
                            curKnot.y + (acc.last().y - curKnot.y).coerceIn(-1, 1)
                        )
                    }
                    else
                        curKnot
                }

                yield(lastRope)
            }
        }

    fun moveRope(size: Int, input: List<String>): Int {
        val tailPositions = mutableSetOf(Pos(0,0))
        var rope = List(size) { Pos(0,0) }
        input.forEach { command ->
            move(rope, command).forEach {
                rope = it
                tailPositions.add(it.last())
            }
        }
        return tailPositions.size
    }
    fun part1(input: List<String>) = moveRope(2, input)
    fun part2(input: List<String>) = moveRope(10, input)

    val input = readInput("day9")
    println(part1(input))
    println(part2(input))
}
