fun main() {

    fun findLowestPoints(input: List<String>): Pair<List<Int>, List<Point>>{
        val board = input.map { it.toCharArray().map { it2 -> it2.toString().toInt() } }
        val lowestPoints = mutableListOf<Int>()
        val lowestPointsIndex = mutableListOf<Point>()
        for(i in board.indices){
            for(j in board[i].indices){
                val testPoint = board[i][j]
                val neighbourPoint = mutableListOf<Int>()
                val neighbourPointIndexes = listOf(
                    Pair(i-1, j), Pair(i+1,j), Pair(i, j-1), Pair(i, j+1)
                )
                neighbourPointIndexes.forEach {
                    if (it.first>=0 && it.first<board.size && it.second>=0 && it.second<board[i].size){
                        neighbourPoint.add(board[it.first][it.second])
                    }
                }
                if (neighbourPoint.all{it>testPoint}){
                    lowestPoints.add(testPoint)
                    lowestPointsIndex.add(Point(j, i))
                }
            }
        }
        return Pair(lowestPoints, lowestPointsIndex)
    }

    fun part1(input: List<String>): Int {
        val (lowestPoints, _) = findLowestPoints(input)
        return lowestPoints.sumOf { it+1 }
    }

    fun part2(input: List<String>): Int {
        val board = input.map { it.toCharArray().map { it2 -> it2.toString().toInt() } }
        val maxYsize = board.size
        val maxXsize = board[0].size
        val (_, lowestPointsIndex) = findLowestPoints(input)
        val poolSizeList = mutableListOf<Int>()
        for(pointIndex in lowestPointsIndex){
            var poolPoints = mutableListOf(pointIndex)
            var lastPoolPoints = listOf<Point>()
            while (poolPoints.size != lastPoolPoints.size){
                lastPoolPoints = poolPoints.toList()
                val tmpPoolPoints = poolPoints.toMutableList()
                poolPoints.forEach{ point ->
                    val neighbourPointIndexes = listOf(
                        Point(point.x-1, point.y), Point(point.x+1,point.y),
                        Point(point.x, point.y-1), Point(point.x, point.y+1)
                    )
                    neighbourPointIndexes.forEach{ neighbour ->
                        if (
                            neighbour.x in 0 until maxXsize
                            && neighbour.y in 0 until maxYsize
                            && board[neighbour.y][neighbour.x] < 9
                            && Point(neighbour.x, neighbour.y) !in tmpPoolPoints
                        ){
                            tmpPoolPoints.add(Point(neighbour.x, neighbour.y))
                        }
                    }

                }
                poolPoints = tmpPoolPoints
            }
            poolSizeList.add(poolPoints.size)
        }
        val sortedPoolSizeList = poolSizeList.sorted().reversed()
        return sortedPoolSizeList[0]*sortedPoolSizeList[1]*sortedPoolSizeList[2]
    }

    // test if implementation meets criteria from the description, like:
    val day = 9
    val testInput = readInput("Day0${day}_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)
    val input = downloadAndReadDayInput(day)
    println(part1(input))
    println(part2(input))
}
