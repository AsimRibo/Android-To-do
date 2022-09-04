package hr.asimr.todo.utils

import android.os.Environment

fun isExternalStorageWriteable() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

fun isExternalStorageReadable() = isExternalStorageWriteable() || Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()