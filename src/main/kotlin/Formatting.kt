import java.text.DecimalFormat

// Форматирование чисел, больших maxNum, в экспоненциальную запись
fun formatScaleNum(num: Int, maxNum: Int): String {
    return if (num < maxNum) num.toString()
    else DecimalFormat("0.0E0").format(num).replace(',', '.')
}

// Форматирование строк, имеющих длину больше num, в следующую запись: <num Символов>..."
fun formatString(string: String, num: Int): String {
    return if (string.length <= num) string else "${string.dropLast(string.length - num)}..."
}