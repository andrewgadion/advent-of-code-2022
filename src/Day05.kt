import java.util.*
import kotlin.io.path.createTempDirectory

fun main() {
    fun parseCrates(input: List<String>): List<MutableList<Char>> {
        val crates = input.reversed().drop(1)
        val cratesStr = input.last()
        val cratesCount = cratesStr.trim().split(" ").last().toInt()
        return (1..cratesCount).map { n ->
            val index = cratesStr.indexOf(n.toString())
            crates.mapNotNull { it.getOrNull(index)?.takeIf(Char::isLetter) }.toMutableList()
        }
    }

    fun move(commandStr: String, crates: List<MutableList<Char>>, keepOrder: Boolean = false) {
        val (count, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)")
            .matchEntire(commandStr)?.groupValues?.drop(1)?.map(String::toInt)!!
        var elementsToMove = buildList { repeat(count) { add(crates[from - 1].removeLast()) } }
        if (keepOrder) elementsToMove = elementsToMove.reversed()
        elementsToMove.forEach(crates[to - 1]::add)
    }

    fun part1(input: List<String>, keepOrder: Boolean = false): String {
        val createsInput = input.takeWhile(String::isNotBlank)
        val crates = parseCrates(createsInput)
        input.drop(createsInput.size + 1).forEach {
            move(it, crates, keepOrder)
        }
        return crates.fold("") { acc, cur -> acc + cur.last() }
    }
    fun part2(input: List<String>) = part1(input, true)

    val input = readInput("day5")
    println(part1(input))
    println(part2(input))
}
