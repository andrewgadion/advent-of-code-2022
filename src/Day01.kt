fun main() {
    fun allCalories(input: List<String>) = buildList<Long> {
        add(0)
        input.forEach { if (it.isBlank()) add(0) else this[lastIndex] += it.toLong() }
    }

    fun part1(input: List<String>): Long {
        return allCalories(input).max()
    }

    fun part2(input: List<String>): Long {
        return allCalories(input).sortedDescending().take(3).sum()
    }

    val input = readInput("day1")
    println(part1(input))
    println(part2(input))
}
