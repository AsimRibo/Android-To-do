package hr.asimr.todo.models

import android.content.Context
import android.util.Log
import hr.asimr.todo.utils.isExternalStorageReadable
import hr.asimr.todo.utils.isExternalStorageWriteable
import java.io.File
import java.lang.Exception

private const val DIR = "todo"
private const val FILE = "tasks.txt"

class TaskList(private val context: Context) : ArrayList<Task>() {

    fun saveInFile() {
        if (!isExternalStorageWriteable()) return

        try {
            File(context.getExternalFilesDir(DIR), FILE)
                .bufferedWriter().use { bw ->
                    for (task in this) {
                        bw.write(task.prepareForFileLine())
                    }
                }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }

    fun readFromFile() {
        if (!isExternalStorageReadable()) return

        try {
            File(context.getExternalFilesDir(DIR), FILE)
                .useLines { lines ->
                    lines.forEach { line ->
                        add(Task.parseFromFileLine(line))
                    }
                }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }
}