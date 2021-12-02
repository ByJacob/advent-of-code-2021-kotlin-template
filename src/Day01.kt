fun main() {
    fun part1(input: List<String>): Int {
        var increaseCount = 0
        for (i in 1 until input.size) {
            if (input[i - 1].toInt() < input[i].toInt()) {
                increaseCount += 1
            }
        }
        return increaseCount
    }

    fun part2(input: List<String>): Int {
        var increaseCount = 0
        for (i in 3 until input.size) {
            val sum1 = input[i - 3].toInt() + input[i - 2].toInt() + input[i - 1].toInt()
            val sum2 = input[i-2].toInt() + input[i-1].toInt() + input[i].toInt()
            if (sum1<sum2){
                increaseCount +=1
            }
        }
        return increaseCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = downloadAndReadDayInput(1)
    println(part1(input))
    println(part2(input))
}
