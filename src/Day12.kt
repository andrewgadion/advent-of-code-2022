import java.util.PriorityQueue

data class MapPos(val point: Pair<Int, Int>, val distance: Int)

fun main() {
    fun Char.isEnd(): Boolean = this == 'E'
    fun Char.canStep(d: Char): Boolean {
        if (this == 'S') return true
        if (d.isEnd()) return this == 'z'
        return d.code - this.code <= 1
    }

    fun List<String>.neighbours(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (row, col) = point
        return listOf(row to col - 1, row - 1 to col, row to col + 1, row + 1 to col)
            .filter { (itR, itC) ->
                this.getOrNull(itR)?.getOrNull(itC)?.let(this[row][col]::canStep) ?: false
            }
    }

    fun findPath(initial: Pair<Int, Int>, input: List<String>): Int {
        val visited = mutableSetOf(initial)
        val paths = PriorityQueue<MapPos> { o1, o2 -> o1.distance - o2.distance }
        paths.add(MapPos(initial, 0))
        while (paths.isNotEmpty()) {
            val cur = paths.poll()
            if (input[cur.point.first][cur.point.second].isEnd())
                return cur.distance
            input.neighbours(cur.point).filterNot(visited::contains).forEach {
                paths.add(MapPos(it, cur.distance + 1))
                visited.add(it)
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val startRow = input.indexOfFirst { it.contains('S') }
        val startCol = input[startRow].indexOf('S')
        return findPath(startRow to startCol, input)
    }

    fun part2(input: List<String>): Int {
        return input.flatMapIndexed { col: Int, rowStr: String ->
            rowStr.mapIndexed { row: Int, c: Char ->
                if (c == 'a') findPath(col to row, input) else -1
            }.filterNot { it == -1 }
        }.min()
    }

    val input = readInput("day12")
    println(part1(input))
    println(part2(input))
}
