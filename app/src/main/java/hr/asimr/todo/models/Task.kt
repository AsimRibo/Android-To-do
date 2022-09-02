package hr.asimr.todo.models

private const val DEL = "|"

data class Task(val details: String, val done: Boolean) {

    fun prepareForFileLine() = String.format("%s%s%b\n", details, DEL, done)

    companion object {
        fun parseFromFileLine(text: String): Task =
            text.split(DEL).let {
                Task(it[0], it[1].toBoolean())
            }
    }
}