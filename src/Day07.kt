import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {

    fun parseInput(input: List<String>): List<Int>{
        return input[0].split(",").map { it.toInt() }
    }

    fun sortingSelection(array: MutableList<Int>): MutableList<Int> {

        for (i in 0 until array.size - 1) {
            var min = i
            for (j in i+1 until array.size) {
                if(array[j] < array[min]) {
                    min = j
                }
            }

            val temp = array[i]
            array[i] = array[min]
            array[min] = temp
        }

        return array
    }

    fun median(array: MutableList<Int>): Double {
        sortingSelection(array)

        return if (array.size % 2 == 0) {
            ((array[array.size / 2] + array[array.size / 2 - 1]) / 2).toDouble()
        } else {
            (array[array.size / 2]).toDouble()
        }
    }

    fun calculateSD(numArray: List<Int>): Int {
        var sum = 0.0
        var standardDeviation = 0.0

        for (num in numArray) {
            sum += num
        }

        val mean = sum / numArray.size

        for (num in numArray) {
            standardDeviation += (num - mean).pow(2.0)
        }

        return sqrt(standardDeviation / numArray.size).toInt()
    }

    fun part1(input: List<String>): Int {
        val list = parseInput(input)
        val mod = median(list.toMutableList()).toInt()
        return list.sumOf { abs(it - mod) }
    }

    fun part2(input: List<String>): Int {
        val list = parseInput(input)
        val fuelList = mutableListOf<Int>()
        for (i in list.minOf { it }..list.maxOf { it }){
            fuelList.add(list.map{abs(it-i)}.sumOf { (0..it).sum() })
        }
        return fuelList.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val day = 7
    val testInput = readInput("Day0${day}_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    val input = downloadAndReadDayInput(day)
    println(part1(input))
    println(part2(input))
}
