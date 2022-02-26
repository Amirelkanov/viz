import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class VisualiseTests {

    private val standardOut = System.out
    private val standardIn = System.`in`
    private val stream = ByteArrayOutputStream()

    @BeforeTest
    fun setUp() {
        System.setOut(PrintStream(stream))
    }

    @AfterTest
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
    }

    @Test
    fun parseArgsTest1() {
        val out = "\u001b[31mПроверьте корректность ввода данных\u001B[0m"
        visualise("Don't care", null, null)
        assertEquals(out, stream.toString().trim())
    }

    @Test
    fun parseArgsTest2() {
        val out = "\u001b[31mПроверьте корректность ввода данных\u001B[0m"
        visualise("Don't care", null, "1.png")
        assertEquals(out, stream.toString().trim())
    }

    @Test
    fun parseArgsTest3() {
        val out = "\u001b[31mПроверьте корректность ввода данных\u001B[0m"
        visualise("Don't care", hashMapOf("Test" to 12f), null)
        assertEquals(out, stream.toString().trim())
    }
}