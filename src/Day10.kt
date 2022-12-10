fun main() {
    fun interpret(input: List<String>) = sequence {
        var x = 1L
        input.forEach {
            yield(x)
            if (it.startsWith("addx")) {
                yield(x)
                x += it.split(" ")[1].toLong()
            }
        }
    }

    fun part1(input: List<String>) = interpret(input)
        .mapIndexed { index, l -> index + 1 to l }
        .filter { (20..220 step 40).contains(it.first) }
        .sumOf { it.first * it.second }

    fun part2(input: List<String>) = interpret(input)
        .chunked(40)
        .joinToString("\n") {
            it.mapIndexed { pI, x ->
                if ((x..x + 2).contains(pI + 1)) "#" else "."
            }.joinToString("")
        }

    val input = readInput("day10")
    println(part1(input))
    println(part2(input))
}
