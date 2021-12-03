fun main() {
    fun part1(input: List<String>): Int {

        var gamma = ""
        var epsilon = ""

        for (i in 0 until input[0].length){
            var count0 = 0
            var count1 = 0
            input.forEach {
                if (it[i].toString().toInt() == 0){
                    count0 += 1
                } else {
                    count1 += 1
                }
            }
            if (count0>count1){
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }

        return Integer.parseInt(gamma, 2)*Integer.parseInt(epsilon, 2);
    }

    fun countBits(input: List<String>): HashMap<Int, HashMap<Int, Int>> {
        val bits : HashMap<Int, HashMap<Int, Int>> = HashMap()
        input.forEach {
            it.map { it2 -> it2.toString().toInt() }.forEachIndexed { index, i ->
                if (index !in bits){
                    bits[index] = hashMapOf(0 to 0, 1 to 0)
                }
                bits[index]?.set(i, bits[index]?.get(i)?.plus(1) ?: 0)
            }
        }
        return bits
    }

    fun part2(input: List<String>): Int {

        var oxygenList = input.toMutableList()
        var co2List = input.toMutableList()

        for (i in 0 until input[0].length){
            val bits = countBits(oxygenList)
            oxygenList = oxygenList.filter {
                var maxValue = bits[i]!!.maxByOrNull { it2 -> it2.value }!!.key
                if (bits[i]!![0] == bits[i]!![1]) {
                    maxValue = 1
                }
                it[i].toString().toInt() == (maxValue)
            }.toMutableList()
            if (oxygenList.size == 1){
                break
            }
        }

        for (i in 0 until input[0].length){
            val bits = countBits(co2List)
            co2List = co2List.filter {
                var minValue = bits[i]!!.minByOrNull { it2 -> it2.value }!!.key
                if (bits[i]!![0] == bits[i]!![1]) {
                    minValue = 0
                }
                it[i].toString().toInt() == (minValue)
            }.toMutableList()
            if (co2List.size == 1){
                break
            }
        }

        return Integer.parseInt(oxygenList[0], 2)*Integer.parseInt(co2List[0], 2);
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = downloadAndReadDayInput(3)
    println(part1(input))
    println(part2(input))
}
