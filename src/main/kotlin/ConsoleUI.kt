// Обработка данных, введенных пользователем в UI
fun uiInput() {
    println("Добро пожаловать в утилиту AmEl VIZ, позволяющую визуализировать ваши данные!")
    println("Для получения более подробной информации о возможностях программы введите команду help\n")

    print("Введите запрос: ")
    val input = readLine()

    if (input != null && input.isNotEmpty()) {
        parseArgs(input.split(' ').toTypedArray())
    } else negativePrint("Проверьте корректность ввода данных")
}

fun visualise(command: String, data: HashMap<String, Float>?, imageToSave: String?) {
    if (data != null && imageToSave != null) {
        createWindow("AmEl VIZ", data, command, imageToSave)
    } else negativePrint("Проверьте корректность ввода данных")
}