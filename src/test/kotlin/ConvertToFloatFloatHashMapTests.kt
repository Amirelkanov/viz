import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConvertToFloatFloatHashMapTests {

    @Test
    fun convertToFloatFloatHashMapTest1() {
        val inputHashMap = hashMapOf(
            "20.6" to 50f,
            "23.4" to 398.4f
        )
        val res = hashMapOf(
            20.6f to 50.0f,
            23.4f to 398.4f
        )
        assertEquals(res, convertToFloatFloatHashMap(inputHashMap))
    }

    @Test
    fun convertToFloatFloatHashMapTest2() {
        val inputHashMap = hashMapOf(
            "20" to 50f,
            "23.4" to 398.4f
        )
        val res = hashMapOf(
            20.0f to 50.0f,
            23.4f to 398.4f
        )
        assertEquals(res, convertToFloatFloatHashMap(inputHashMap))
    }
}