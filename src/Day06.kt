fun main() {
    fun String.distinct() = this.toSet().size == this.length
    fun markerIndex(str: String, window: Int) = str.windowed(window, 1).indexOfFirst(String::distinct) + window
    fun part1(input: List<String>) = markerIndex(input[0], 4)
    fun part2(input: List<String>) = markerIndex(input[0], 14)

    val input = readInput("day6")
    println(part1(input))
    println(part2(input))
}
