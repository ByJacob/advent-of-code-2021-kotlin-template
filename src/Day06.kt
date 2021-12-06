fun main() {

    fun part1(input: List<String>, estimateDay: Int): Long {
        val fishCount = input[0].split(",").map { it.toInt() }
        val fishMap = hashMapOf<Int,Long>().toMutableMap()
        for(i in 0..8){
            fishMap[i]=(0).toLong()
        }
        for(fish in fishCount){
            fishMap[fish] = fishMap[fish]!!.plus(1)
        }
//        println("Initial state: $fishMap")
        for (i in 0 until estimateDay) {
//            println("Day: $i")
            val fishMapTmp: MutableMap<Int, Long> = HashMap(fishMap)
            for(j in 0 until 8){
                fishMap[j] = fishMapTmp.getValue(j+1)
            }
            fishMap[6] = fishMap[6]!! + fishMapTmp[0]!!
            fishMap[8] = fishMapTmp[0]!!
//            println("After ${i+1} day: $fishMap")
        }
        return fishMap.values.sum()
    }

    fun part2(input: List<String>, estimateDay: Int): Long {
        return part1(input, estimateDay).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val day = 6
    val testInput = readInput("Day0${day}_test")
    check(part1(testInput, 18) == 26L)
    check(part1(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539)

    val input = downloadAndReadDayInput(day)
    println(part1(input, 80))
    println(part2(input, 256))
}
