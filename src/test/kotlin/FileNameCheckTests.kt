import kotlin.test.*

internal class FileNameCheckTests {

    @Test
    fun fileNameCheckTest1() {
        val filename = "1.c"
        assertEquals(true, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest2() {
        val filename = "README"
        assertEquals(false, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest3() {
        val filename = "Тест.txt"
        assertEquals(true, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest4() {
        val filename = "Тест.эс"
        assertEquals(false, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest5() {
        val filename = "."
        assertEquals(false, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest6() {
        val filename = "Слово.123"
        assertEquals(false, fileNameCheck(filename))
    }

    @Test
    fun fileNameCheckTest7() {
        val filename = ".txt"
        assertEquals(true, fileNameCheck(filename))
    }

}
