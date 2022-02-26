import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.swing.Swing
import org.jetbrains.skiko.SkiaWindow
import java.awt.Dimension
import javax.swing.WindowConstants

fun createWindow(title: String, data: HashMap<String, Float>, command: String, imageToSave: String) =
    runBlocking(Dispatchers.Swing) {
        val window = SkiaWindow()
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.title = title
        window.layer.renderer = Renderer(window.layer, data, command, imageToSave)

        window.preferredSize = Dimension(600, 500)
        window.minimumSize = Dimension(100, 100)

        window.pack()
        window.isVisible = true
    }
