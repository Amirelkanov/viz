import org.jetbrains.skija.Color
import kotlin.random.Random
import kotlin.system.exitProcess

// HashMap<String, Float> -> HashMap<Float, Float>
fun convertToFloatFloatHashMap(hashMapToConvert: HashMap<String, Float>): HashMap<Float, Float> {
    val floatData = hashMapOf<Float, Float>()
    hashMapToConvert.forEach {
        if (it.key.toFloatOrNull() != null) floatData[it.key.toFloat()] = it.value
        else {
            negativePrint("Формат файла неверен для данной диаграммы.")
            exitProcess(0)
        }
    }
    return floatData
}

fun getRandomColorList(size: Int): MutableList<Int> {
    val randomColorList = mutableListOf<Int>()
    repeat(size) { randomColorList.add(Color.makeRGB(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))) }
    return randomColorList
}