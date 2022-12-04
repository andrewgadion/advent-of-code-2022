fun main() {
    fun parseRange(str: String) = str.split("-").let { it[0].toLong()..it[1].toLong() }
    fun parse(input: List<String>) = input.map { it.split(',').map(::parseRange) }

    fun LongRange.contains(range: LongRange) = this.first <= range.first && this.last >= range.last
    fun LongRange.intersects(range: LongRange) = range.contains(this.first) || this.contains(range.first)

    fun part1(input: List<String>) = parse(input)
        .filter { it[0].contains(it[1]) || it[1].contains(it[0]) }
        .size

    fun part2(input: List<String>) = parse(input).filter { it[0].intersects(it[1]) }.size

    val input = readInput("day4")
    println(part1(input))
    println(part2(input))
}
