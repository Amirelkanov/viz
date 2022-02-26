import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.*

internal class ParseArgsTests {

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
        parseArgs(arrayOf("ThisCommandDoesn'tExist"))
        assertEquals(out, stream.toString().trim())
    }

    @Test
    fun parseArgsTest2() {
        val out = """
        -------------------------------------------------------------
        Запросы визуализации данных строятся следующим образом:
            <@Команда> <Файл, который нужно обработать> <Файл, в который нужно сохранить результат>
        Например: HG data/Test.amel 1.png
        
        Вы можете использовать следующие команды (маркеры для команд виз. данных обозначены как @):
        * Help - вывод информации о всех возможных командах
        @ HG - построение гистограммы
        @ CDG - построение круговой диаграммы
        @ SP - построение диаграммы рассеяния
        -------------------------------------------------------------
    """.trimIndent()
        parseArgs(arrayOf("help"))
        assertEquals(out, stream.toString().trim())
    }

    @Test
    fun parseArgsTest3() {
        val out = "\u001b[31mПроверьте корректность ввода данных\u001B[0m"
        parseArgs(arrayOf("This", "Command", "Doesn'tExist"))
        assertEquals(out, stream.toString().trim())
    }
}