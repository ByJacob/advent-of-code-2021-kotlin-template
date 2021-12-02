
import java.io.File
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun downloadAndReadDayInput(day: Number, year: Number = 2021): List<String> {
    val filename = "Day${day.toString().padStart(2, '0')}.txt"
    val file = File("src",filename)
    if (!file.exists()) {
        val fileUrl = "https://adventofcode.com/$year/day/$day/input"
        val url = URL(fileUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 300000
        connection.doOutput = true
        val session_id = System.getenv("session") ?: "ENV_IS_EMPTY"
        connection.setRequestProperty("cookie", "session=$session_id")
        val data = connection.inputStream.bufferedReader().readText()
        file.writeText(data)
    }
    return file.readLines()
}