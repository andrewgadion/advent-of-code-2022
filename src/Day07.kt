abstract class Entry(val name: String) { abstract val size: Long }
class File(name: String, override val size: Long) : Entry(name)
class Dir(name: String, val parent: Dir?) : Entry(name) {
    private val entries = mutableListOf<Entry>()
    override val size: Long get() = entries.sumOf(Entry::size)
    fun addEntry(entry: Entry) = entries.add(entry)
    fun findDir(name: String): Dir = entries.first { it.name == name } as Dir
    fun dirs(): Sequence<Dir> = sequence {
        yield(this@Dir)
        entries.filterIsInstance<Dir>().forEach { yieldAll(it.dirs()) }
    }
}
const val CD = "$ cd"
const val LS = "$ ls"
fun main() {
    fun cd(command: String, curDir: Dir, rootDir: Dir) =
        when (val cdArg = command.substringAfter(CD).trim()) {
            "/" -> rootDir
            ".." -> curDir.parent!!
            else -> curDir.findDir(cdArg)
        }

    fun ls(curDir: Dir, output: List<String>) {
        output.map {
            if (it.startsWith("dir"))
                Dir(it.substringAfter("dir").trim(), curDir)
            else {
                val (size, name) = it.split(" ").map(String::trim)
                File(name, size.toLong())
            }
        }.forEach(curDir::addEntry)
    }

    fun parse(input: List<String>): Dir {
        val root = Dir("/", null)
        var curDir: Dir = root
        var index = 0
        while (index < input.size) {
            val command = input[index++]
            when {
                command.startsWith(CD) -> curDir = cd(command, curDir, root)
                command.startsWith(LS) -> {
                    val output = input.drop(index).takeWhile { !it.startsWith("$") }
                    ls(curDir, output)
                    index += output.size
                }
            }
        }
        return root
    }

    fun part1(input: List<String>) = parse(input).dirs().filter { it.size < 100000 }.sumOf(Dir::size)
    fun part2(input: List<String>): Long {
        val root = parse(input)
        val spaceToFree = 30000000 - (70000000 - root.size)
        return root.dirs().filter { it.size >= spaceToFree }.minBy(Dir::size).size
    }

    val input = readInput("day7")
    println(part1(input))
    println(part2(input))
}
