fun main() {
    fun parse(input: List<String>) = input.map { it.map(Char::digitToInt) }
    fun List<List<Int>>.top(colRow: Pair<Int, Int>) =
        (colRow.first - 1 downTo 0).map { this[it][colRow.second] }
    fun List<List<Int>>.bottom(colRow: Pair<Int, Int>) =
        (colRow.first + 1 .. lastIndex).map { this[it][colRow.second] }
    fun List<List<Int>>.left(colRow: Pair<Int, Int>) =
        (colRow.second - 1 downTo 0).map { this[colRow.first][it] }
    fun List<List<Int>>.right(colRow: Pair<Int, Int>) =
        (colRow.second + 1 .. this[colRow.first].lastIndex).map { this[colRow.first][it] }

    fun List<List<Int>>.isVisible(colRow: Pair<Int, Int>): Boolean {
        val height = this[colRow.first][colRow.second]
        return top(colRow).all { it < height } ||
                bottom(colRow).all { it < height } ||
                left(colRow).all { it < height } ||
                right(colRow).all { it < height }
    }

    fun maxByDirection(height: Int, direction: List<Int>) =
        direction.indexOfFirst { it >= height }.let { if (it == -1) direction.size else it + 1 }

    fun List<List<Int>>.viewScore(colRow: Pair<Int, Int>): Int {
        val height = this[colRow.first][colRow.second]
        return maxByDirection(height, top(colRow)) *
                maxByDirection(height, bottom(colRow)) *
                maxByDirection(height, left(colRow)) *
                maxByDirection(height, right(colRow))
    }

    fun List<List<Int>>.iterate() =
        (0..lastIndex).asSequence().flatMap { col ->
            (0..this[col].lastIndex).asSequence().map { row -> col to row }
        }

    fun part1(input: List<String>) = parse(input).let { trees -> trees.iterate().count(trees::isVisible) }
    fun part2(input: List<String>) = parse(input).let { trees -> trees.iterate().maxOf(trees::viewScore) }

    val input = readInput("day8")
    println(part1(input))
    println(part2(input))
}
