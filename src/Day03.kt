fun main() {
    fun Char.rank() = if (this.isLowerCase()) this - 'a' + 1 else this - 'A' + 27

    fun sameChar(strings: List<String>): Char =
        strings.first().find { c ->
            strings.drop(1).all { it.contains(c) }
        } ?: throw Exception("no same char")

    fun part1(input: List<String>) = input.sumOf { sameChar(it.chunked(it.length / 2)).rank() }

    fun part2(input: List<String>) = input.chunked(3).sumOf { sameChar(it).rank() }

    val input = readInput("day3")
    println(part1(input))
    println(part2(input))
}
