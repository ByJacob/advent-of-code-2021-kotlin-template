fun main() {

    fun processInput(input: List<String>): Pair<List<Int>, List<List<IntArray>>> {
        val drawList = input[0].split(",").map { it.toInt() }
        val boards = mutableListOf<List<IntArray>>()

        for (i in 2 until input.size step 6) {
            val rows = mutableListOf<IntArray>()
            for(j in i until i+5) {
                val columns = input[j]
                    .split(" ")
                    .filter{ it.isNotEmpty() }
                    .map { it.toInt() }
                    .toIntArray()
                rows.add(columns)
            }
            boards.add(rows.toList())
        }

        return Pair(drawList, boards.toList())

    }

    fun markNumber(board: List<IntArray>, number: Int): List<IntArray> {
        return board.map{
            it.map { it2 ->
                if(it2 == number)
                    -1
                else
                    it2
            }.toIntArray()
        }
    }

    fun detectBingo(board: List<IntArray>): Boolean {
        for(i in 0 until 5){
            val column = board.map { it.get(i) }
            if(board[i].all{it<0} or column.all{it<0}){
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var (drawList, boards) = processInput(input)
        var lastNumber = 0
        var bingoBoardIndex = -1
        drawList.forEach { draw ->
            val boardsMaskBingo = boards.map { detectBingo(it) }
            if(boardsMaskBingo.any{it}){
                bingoBoardIndex = boardsMaskBingo.indexOf(true)
            } else {
                lastNumber = draw
                boards = boards.map { board ->
                    markNumber(board, draw)
                }
            }
        }
        val sumNoBingoNumbers = boards[bingoBoardIndex]
            .flatMap { it.toList() }
            .filter { it>=0 }
            .sum()
        return sumNoBingoNumbers*lastNumber
    }

    fun part2(input: List<String>): Int {
        var (drawList, boards) = processInput(input)
        var lastNumber = 0
        var bingoBoardIndex = -1
        drawList.forEach { draw ->
            val boardsMaskBingo = boards.map { detectBingo(it) }
            if(boardsMaskBingo.all{it}){
                return@forEach
            }
            lastNumber = draw
            bingoBoardIndex = boardsMaskBingo.indexOf(false)
            boards = boards.map { board ->
                markNumber(board, draw)
            }
        }
        val sumNoBingoNumbers = boards[bingoBoardIndex]
            .flatMap { it.toList() }
            .filter { it>=0 }
            .sum()
        return sumNoBingoNumbers*lastNumber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = downloadAndReadDayInput(4)
    println(part1(input))
    println(part2(input))
}
