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
        if (!isExternalStorageWriteable()) {
            Log.i("Storage", "No permissions to perform file save.")
            return
        }

        try {
            File(context.getExternalFilesDir(DIR), FILE)
                .bufferedWriter().use { bw ->
                    for (task in this) {
                        bw.write(task.prepareForFileLine())
                    }
                }
            Log.i("Storage", "File written")
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }

    fun readFromFile() {
        if (!isExternalStorageReadable()) {
            Log.i("Storage", "No permissions to perform file read.")
            return
        }

        try {
            File(context.getExternalFilesDir(DIR), FILE)
                .useLines { lines ->
                    lines.forEach { line ->
                        add(Task.parseFromFileLine(line))
                    }
                }
            Log.i("Storage", "File read")
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString(), e)
        }
    }
}