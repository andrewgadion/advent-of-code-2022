import java.util.LinkedList

fun Long.isDivisible(other: Long) = this % other == 0L
class Monkey(val inspect: Command, val divisor: Long, val ifTrueMonkey: Int, val ifFalseMonkey: Int) {
    private val items = LinkedList<Long>()
    var inspectCount = 0
        private set
    fun addItem(item: Long) = items.add(item)
    fun run(allMonkeys: List<Monkey>, decreaseWorryLevel: (Long) -> Long) {
        while (items.isNotEmpty()) {
            val item = decreaseWorryLevel(inspect.run(items.poll()))
            inspectCount++
            val monkey = if (item.isDivisible(divisor)) ifTrueMonkey else ifFalseMonkey
            allMonkeys[monkey].addItem(item)
        }
    }
}
class Command(val arg: Long?, val operation: (Long, Long) -> Long) {
    fun run(old: Long) = operation(old, arg ?: old)
}

fun parseCommand(command: String): Command {
    val (_, op, arg) = command.split("=")[1].trim().split(" ").map(String::trim)
    return Command(
        arg.takeIf { it != "old" }?.toLong(),
        when (op) {
            "+" -> Long::plus
            "*" -> Long::times
            else -> throw Exception("unknown operation")
        }
    )
}

fun main() {
    fun parseMonkey(input: List<String>): Monkey {
        val items = input[1].substringAfter("Starting items:").split(",").map { it.trim().toLong() }
        val operation = parseCommand(input[2])
        val divisor = input[3].substringAfter("divisible by").trim().toLong()
        val ifTrueMonkey = input[4].substringAfter("monkey").trim().toInt()
        val ifFalseMonkey = input[5].substringAfter("monkey").trim().toInt()
        return Monkey(operation, divisor, ifTrueMonkey, ifFalseMonkey).also { items.forEach(it::addItem) }
    }

    fun part1(input: List<String>): Long {
        val monkeys = input.chunked(7).map(::parseMonkey)
        repeat(20) { monkeys.forEach { it.run(monkeys) { v -> v / 3 } } }
        return monkeys.map { it.inspectCount }.sortedDescending().take(2).fold(1L, Long::times)
    }
    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(7).map(::parseMonkey)
        val allDivisorsMultiplication = monkeys.fold(1L) { acc, cur -> acc * cur.divisor }
        repeat(10000) { monkeys.forEach { it.run(monkeys) { v -> v % allDivisorsMultiplication} } }
        return monkeys.map { it.inspectCount }.sortedDescending().take(2).fold(1L, Long::times)
    }

    val input = readInput("day11")
    println(part1(input))
    println(part2(input))
}
