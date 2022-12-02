enum class Shape(val Score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3);

    fun Play(other: Shape): Int {
        if (other == this)
            return 3 + Score
        return when(this) {
            Rock -> if (other == Scissors) 6 else 0
            Paper -> if (other == Rock) 6 else 0
            Scissors -> if (other == Paper) 6 else 0
        } + Score
    }

    fun GetAltShape(str: String): Shape {
        return when(str.trim()) {
            "X" -> when(this) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }
            "Y" -> this
            "Z" -> when(this) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }

            else -> throw Exception("Unknown $str")
        }
    }

    companion object {
        fun Parse(str: String): Shape = when(str.trim()) {
            "A", "X" -> Rock
            "B", "Y" -> Paper
            "C", "Z" -> Scissors
            else -> throw Exception("Unknown $str")
        }
    }
}
fun main() {

    fun part1(input: List<String>): Long {
        return input.fold(0) { acc, cur ->
            val shapes = cur.split(" ").map(Shape::Parse)
            acc + shapes[1].Play(shapes[0])
        }
    }

    fun part2(input: List<String>): Long {
        return input.fold(0) { acc, cur ->
            val (a, b) = cur.split(" ")
            val shape = Shape.Parse(a)
            acc + shape.GetAltShape(b).Play(shape)
        }
    }

    val input = readInput("day2")
    println(part1(input))
    println(part2(input))
}
