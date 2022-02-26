import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConnectTests {

    @Test
    fun connectTest1() {
        val res = null
        assertEquals(res, connect("ThisFileDoesn'tExist.amel"))
    }

    @Test
    fun connectTest2() {
        val res = hashMapOf(
            "I'm the only one" to 1f
        )
        assertEquals(res, connect("data/Test1.amel"))
    }

    @Test
    fun connectTest3() {
        val res = hashMapOf(
            "Test1" to 14.6f,
            "Test2" to 15.4f,
            "Test3" to 1.3f,
            "Test4" to 46.9f
        )
        assertEquals(res, connect("data/Test4.amel"))
    }

    @Test
    fun connectTest4() {
        val res = hashMapOf(
            "Test1" to 14.6f,
            "Test2" to 15.4f,
            "Test3" to 1.3f,
            "Test4" to 46.9f,
            "Test5" to 153.2f,
            "Test6" to 12.4f,
            "Test7" to 11.3f,
            "Test8" to 4.9f,
            "Test9" to 4.6f,
            "Test10" to 5.4f
        )
        assertEquals(res, connect("data/Test10.amel"))
    }

    @Test
    fun connectTest5() {
        val res = hashMapOf<String, Float>()
        assertEquals(res, connect("data/TestEmpty.amel"))
    }

    @Test
    fun connectTest6() {
        val res = null
        assertEquals(res, connect("data/BrokenFile.amel"))
    }

}