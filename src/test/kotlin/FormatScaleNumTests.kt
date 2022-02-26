import kotlin.test.Test
import kotlin.test.assertEquals

internal class FormatScaleNumTests {

    @Test
    fun formatScaleNumTest1() {
        val res = "150"
        assertEquals(res, formatScaleNum(150, 10000))
    }

    @Test
    fun formatScaleNumTest2() {
        val res = "9999"
        assertEquals(res, formatScaleNum(9999, 10000))
    }

    @Test
    fun formatScaleNumTest3() {
        val res = "8.3E6"
        assertEquals(res, formatScaleNum(8274687, 10000))
    }

    @Test
    fun formatScaleNumTest4() {
        val res = "1.0E4"
        assertEquals(res, formatScaleNum(10000, 10000))
    }

    @Test
    fun formatScaleNumTest5() {
        val res = "3.5E5"
        assertEquals(res, formatScaleNum(352849, 10000))
    }
}