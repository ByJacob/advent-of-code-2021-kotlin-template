import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)

data class Line(
    val allPoints: List<Point>,
    val maxPointValue: Int,
    val minPointValue: Int,
    val isSlanting: Boolean
)

fun createLineFromPoints(startPoint: Point, endPoint: Point): Line {
    val points = mutableListOf<Point>()
    //y = ax+b
    if (endPoint.x - startPoint.x != 0) {
        val a = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x)
        val b = startPoint.y - a * startPoint.x
        val stepsX = if (startPoint.x < endPoint.x) (startPoint.x..endPoint.x) else (startPoint.x downTo endPoint.x)
        for (x in stepsX) {
            points.add(Point(x, a * x + b))
        }
    } else {
        val stepsY = if (startPoint.y < endPoint.y) (startPoint.y..endPoint.y) else (startPoint.y downTo endPoint.y)
        for (y in stepsY) {
            points.add(Point(startPoint.x, y))
        }
    }
    val maxPointValue = points.maxOf { max(it.x, it.y) }
    val minPointValue = points.minOf { min(it.x, it.y) }
    val isSlanting = (startPoint.x != endPoint.x) and (startPoint.y != endPoint.y)

    return Line(points.toList(), maxPointValue, minPointValue, isSlanting)
}

fun main() {

    fun parseInputToLines(input: List<String>): List<Line> {
        val lines = input.map { dirtLine ->
            val edgeLine = dirtLine.split(" -> ").map { dirtPoints ->
                val points = dirtPoints.split(",").map { it.toInt() }.toList()
                Point(points[0], points[1])
            }.toList()
            createLineFromPoints(edgeLine[0], edgeLine[1])
        }
        return lines
    }

    fun part1(input: List<String>, isSlanting: Boolean = false): Int {
        val lines: List<Line> = if (!isSlanting) {
            parseInputToLines(input).filter { it.isSlanting.not() }
        } else {
            parseInputToLines(input)
        }
        val maxIndexValue = lines.maxOf { it.maxPointValue } + 1
        val board = Array(maxIndexValue) { Array(maxIndexValue) { 0 } }
        lines.forEach { line ->
            line.allPoints.forEach { point ->
                board[point.y][point.x] += 1
            }
        }
//        board.forEach { row ->
//            println(row.joinToString(separator = " "))
//        }
        return board.flatMap { it.toList() }.filter { it > 1 }.size
    }

    fun part2(input: List<String>): Int {
        return part1(input, true)
    }

    // test if implementation meets criteria from the description, like:
    val day = 5
    val testInput = readInput("Day0${day}_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = downloadAndReadDayInput(day)
    println(part1(input))
    println(part2(input))
}
