import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConvertStringTests {

    @Test
    fun convertStringTest1() {
        val res = "String"
        assertEquals(res, formatString("String", 10))
    }

    @Test
    fun convertStringTest2() {
        val res = "Strin..."
        assertEquals(res, formatString("StringString", 5))
    }

    @Test
    fun convertStringTest3() {
        val res = "StringStringStr..."
        assertEquals(res, formatString("StringStringString", 15))
    }

    @Test
    fun convertStringTest4() {
        val res = "a..."
        assertEquals(res, formatString("abcdefghijklmnop", 1))
    }

}