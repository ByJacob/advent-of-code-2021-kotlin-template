fun main() {

    val segments0Count = 6
    val segments0Original = setOf('a','b','c','e','f','g')
    val segments1Count = 2
    val segments1Original = setOf('c','f')
    val segments2Count = 5
    val segments2Original = setOf('a','c','d','e','g')
    val segments3Count = 5
    val segments3Original = setOf('a','c','d','f','g')
    val segments4Count = 4
    val segments4Original = setOf('b','c','d','f')
    val segments5Count = 5
    val segments5Original = setOf('a','b','d','f','g')
    val segments6Count = 6
    val segments6Original = setOf('a','b','d','e','f','g')
    val segments7Count = 3
    val segments7Original = setOf('a','c','f')
    val segments8Count = 7
    val segments8Original = setOf('a','b','c','d','e','f','g')
    val segments9Count = 6
    val segments9Original = setOf('a','b','c','d','f','g')
    val allSegmentsOriginal = listOf(
        segments0Original, segments1Original, segments2Original, segments3Original,segments4Original,
        segments5Original, segments6Original, segments7Original, segments8Original, segments9Original
    )

    fun part1(input: List<String>): Int {
        val filteredList = input.map { it.split("|")[1].split(" ") }
            .flatMap { it.toList() }
            .filter { listOf(segments1Count,segments4Count,segments7Count,segments8Count).indexOf(it.length)>=0  }
        return filteredList.size
    }

    fun returnSegmentsLine(segments: List<String>, number: Number): Set<Char>{
        val segmentsCount = when(number){
            1 -> segments1Count
            4 -> segments4Count
            7 -> segments7Count
            8 -> segments8Count
            else -> -1
        }
        return segments.filter { it.length == segmentsCount }
            .flatMap { it.toCharArray().toList() }
            .distinct().sorted().toSet()
    }

    fun isListCharInString(str: String, charList: Set<Char>): Boolean {
        for(char in charList){
            if(char !in str){
                return false
            }
        }
        return true
    }

    //   aaaa
    //  b    b
    //  b    b
    //   dddd
    //  e    f
    //  e    f
    //   gggg

    fun createMapSegments(line: String): Map<Char, Char>{
        val segments = line.replace(" | "," ")
            .split(" ")
            .filter { it.isNotEmpty() }
            .filter { it.length != segments8Count }
        val segments1 = returnSegmentsLine(segments, 1)
        val segments4 = returnSegmentsLine(segments, 4)
        val segments7 = returnSegmentsLine(segments, 7)
        val segments9 = segments.filter{it.length == segments9Count}
            .filter{isListCharInString(it, segments4)}
            .flatMap { it.toCharArray().toList() }
            .distinct().sorted().toSet()
        val segments9minus4 = segments9.minus(segments4).sorted().toSet()
        val segments3 = segments.filter{it.length == segments3Count}
            .filter { isListCharInString(it, segments1.plus(segments9minus4)) }
            .flatMap { it.toCharArray().toList() }
            .distinct().sorted().toSet()
        val segmentD = segments3.minus(segments1).minus(segments9minus4).distinct()[0]
        val segments0 = segments.filter{it.length == segments0Count}
            .filter {isListCharInString(it, segments8Original.minus(segmentD).toSet()) }
            .flatMap { it.toCharArray().toList() }
            .distinct().sorted().toSet()
        val segmentG = segments9.minus(segments4).minus(segments7).distinct()[0]
        val segmentA = segments9.minus(segments4).minus(segmentG).distinct()[0]
        val segmentE = segments0.minus(segments4).minus(segmentG).minus(segmentA).distinct()[0]
        val segmentB = segments0.minus(segments7).minus(segmentE).minus(segmentG).distinct()[0]
        val segments6 = segments.filter{it.length == segments6Count}
            .filter { isListCharInString(it, setOf(segmentA, segmentD, segmentG, segmentB, segmentE)) }
            .flatMap { it.toCharArray().toList() }
            .distinct().sorted().toSet()
        val segmentF = segments6.minus(setOf(segmentA, segmentB, segmentD, segmentE, segmentG)).distinct()[0]
        val segmentC = segments1.minus(segmentF).distinct()[0]
        return mapOf(
            segmentA to 'a',
            segmentB to 'b',
            segmentC to 'c',
            segmentD to 'd',
            segmentE to 'e',
            segmentF to 'f',
            segmentG to 'g'
        )
    }

    fun detectNumberFromSegments(segment:List<Char>): Number{
        for(originalSegmentNumber in allSegmentsOriginal.indices){
            if(allSegmentsOriginal[originalSegmentNumber].size != segment.size){
                continue
            }
            var isCorrect = true
            for(char in allSegmentsOriginal[originalSegmentNumber]){
                if(char !in segment){
                    isCorrect = false
                    break
                }
            }
            if (isCorrect) {
                return originalSegmentNumber
            }
        }
        throw Exception("Don't find segment $segment !!!")
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (line in input) {
            val segmentsMap = createMapSegments(line)
            val outputNumber = line.split(" | ")[1].split(" ")
                .map { it.map { ch -> segmentsMap[ch]!! } }
                .joinToString(separator = "") { detectNumberFromSegments(it).toString() }.toInt()
            result += outputNumber
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val day = 8
    val testInput = readInput("Day0${day}_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)
    val input = downloadAndReadDayInput(day)
    println(part1(input))
    println(part2(input))
}
