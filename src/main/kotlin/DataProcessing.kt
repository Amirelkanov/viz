import java.io.File


// Подключение к пользовательскому файлу и занесение значений в HashMap
fun connect(filename: String): HashMap<String, Float>? {
    val data = hashMapOf<String, Float>()
    if (File(filename).exists()) {
        File(filename).useLines { lines ->
            lines.forEach { line ->
                val (key, value) = line.split(": ")
                val floatValue = value.trim().toFloatOrNull()

                if (floatValue != null) data[key] = floatValue else return null
            }
        }
        return data
    } else return null
}

// Обработка аргументов запроса
fun parseArgs(args: Array<String>) {
    // Проверка на наличие команды, не относящейся к визуализации диаграммы
    val command = args[0]
    when (command.lowercase()) {
        "help" -> help()
        else -> {
            if (args.size == 3) {
                val data = connect(args[1])
                val imageToSafe = if (fileNameCheck(args[2])) args[2] else null
                visualise(command, data, imageToSafe)
            } else negativePrint("Проверьте корректность ввода данных")
        }
    }
}