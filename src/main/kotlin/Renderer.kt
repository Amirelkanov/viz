import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.skija.*
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaRenderer
import org.jetbrains.skiko.toBufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil
import kotlin.system.exitProcess

typealias legendList = MutableList<Pair<Int, Pair<String, Float>>>

class Renderer(
    private val layer: SkiaLayer, private val dataToAnalyse: HashMap<String, Float>,
    private val command: String, private val imageToSave: String,
) : SkiaRenderer {


    private val typeface = FontMgr.getDefault().matchFamilyStyle("Arial", FontStyle.NORMAL)
    private val font = Font(typeface, 14f)

    private val paint = Paint().apply {
        color = 0xff9BC730L.toInt()
        mode = PaintMode.FILL
        strokeWidth = 8f
    }
    private val fontPaint = Paint().apply {
        color = 0xff878787L.toInt()
        mode = PaintMode.FILL
    }

    private val visualisedDataSize = 350f // Размеры, которые будет иметь диаграмма
    private val padding = 50f
    private val colors = getRandomColorList(dataToAnalyse.size) // Цвета, которые будут использованы в диаграмме

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        val contentScale = layer.contentScale

        canvas.scale(contentScale, contentScale)
        canvas.clear(0xffffffffL.toInt()) // Чтобы скриншоты не были с прозрачным фоном

        parseDrawCommand(canvas)
        takeScreenShot(layer.screenshot())
    }

    // Обработка команд, относящихся к визуализации данных
    private fun parseDrawCommand(canvas: Canvas) {
        when (command.lowercase()) {
            "cdg" -> drawCircleDiagram(canvas)
            "hg" -> drawHistogram(canvas)
            "sp" -> drawScatterPlot(canvas)
            else -> {
                negativePrint("Такой команды не существует.")
                exitProcess(0)
            }
        }
    }

    private fun drawScatterPlot(canvas: Canvas) {
        val floatData = convertToFloatFloatHashMap(dataToAnalyse)
        val (keyMax, valMax) = (floatData.maxByOrNull { it.key }?.key ?: -1f) to
                (floatData.maxByOrNull { it.value }?.value ?: -1f)

        // Отрисовка шкалы
        drawScaleAxis(canvas, valMax, 6)
        drawScaleAxis(canvas, keyMax, 6, isHorizontalAlignment = true)

        // Отрисовка точек
        for ((key, value) in floatData) {
            canvas.drawPoint(key / keyMax * visualisedDataSize + padding,
                value / valMax * visualisedDataSize + padding, paint)
        }
    }

    private fun drawHistogram(canvas: Canvas) {

        val maxi = dataToAnalyse.maxByOrNull { it.value }?.value ?: -1f
        val legend: legendList = mutableListOf()

        drawScaleAxis(canvas, maxi, 6, isReversed = true)

        val barWidth = visualisedDataSize / dataToAnalyse.size
        var xPos = padding
        var index = 0
        for ((key, value) in dataToAnalyse) {
            paint.color = colors[index]
            legend.add(paint.color to Pair(key, value))

            canvas.drawRect(
                Rect(xPos, visualisedDataSize + padding,
                    xPos + barWidth, visualisedDataSize * (1 - (value / maxi)) + padding
                ), paint
            )
            xPos += barWidth
            index++
        }
        drawLegend(canvas, legend.sortedByDescending { it.second.second }.toMutableList())
    }

    private fun drawCircleDiagram(canvas: Canvas) {

        val summa = dataToAnalyse.values.sum()
        val legend: legendList = mutableListOf()

        var index = 0
        var startAngle = 0f
        for ((key, value) in dataToAnalyse) {
            val sweepAngle = 360 * value / summa
            paint.color = colors[index]
            legend.add(paint.color to Pair(key, value))

            canvas.drawArc(
                padding, padding, visualisedDataSize + padding, visualisedDataSize + padding,
                startAngle, sweepAngle, true, paint
            )
            startAngle += sweepAngle
            index++
        }

        drawLegend(canvas, legend.sortedByDescending { it.second.second }.toMutableList())
    }

    private fun drawLegend(canvas: Canvas, legend: legendList) {
        val legendX = visualisedDataSize + padding * 2
        var legendY = padding
        val numOfLegendRows = 10

        // Выводится <numOfLegendRows> легенд, отсортированных по убыванию значений
        for (i in 0 until numOfLegendRows) {
            if (i < legend.size) {
                canvas.drawPoint(legendX, legendY, paint.setColor(legend[i].first))
                canvas.drawString(
                    formatString(legend[i].second.first, 12),
                    legendX + 10f, legendY + 10f,
                    font, fontPaint
                )
                legendY += 40f
            } else break
        }

    }

    /**
     * Отрисовка оси шкалы
     * @param maxi максимальное значение на оси среди переменных
     * @param steps количество делений на шкале (будет +1 steps делений, т.к. нужно еще учитывать 0)
     * @param isHorizontalAlignment построить шкалу горизонтально / вертикально
     * @param isReversed значения на шкале идут сверху вниз / снизу вверх
     * */
    private fun drawScaleAxis(
        canvas: Canvas, maxi: Float, steps: Int,
        isHorizontalAlignment: Boolean = false, isReversed: Boolean = false,
    ) {

        val gridValueIncrement = ceil(maxi / steps).toInt()
        var gridValue = 0

        repeat(steps + 1) {
            val percent = gridValue / maxi
            val grid = visualisedDataSize * (if (isReversed) 1 - percent else percent) + padding

            val (x0, y0, x1, y1) = if (isHorizontalAlignment) {
                mutableListOf(grid, padding / 2, grid, visualisedDataSize + padding)
            } else mutableListOf(10f, grid, visualisedDataSize + padding, grid)

            canvas.drawString(formatScaleNum(gridValue, 10000), x0, y0 - 1, font, fontPaint)
            canvas.drawLine(x0, y0, x1, y1, fontPaint)

            gridValue += gridValueIncrement
        }
    }

    private fun takeScreenShot(screenshot: Bitmap?) {
        if (screenshot != null) {
            val image = screenshot.toBufferedImage()
            ImageIO.write(image, "png", File(imageToSave))
        }
    }
}