package com.example.newsapp.helper.util

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.webkit.MimeTypeMap
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class FileUtil {
    @Throws(Exception::class)
    fun saveFileToDownloadFolder(
        context: Context, bytes: ByteArray,
        fileName: String
    ): File {
        return saveFileToDownloadFolder(context, bytes, cleanUpFileName(fileName), 0)
    }

    @SuppressLint("DefaultLocale")
    @Throws(Exception::class)
    private fun saveFileToDownloadFolder(
        context: Context,
        bytes: ByteArray,
        originalFileName: String,
        existsCount: Int
    ): File {
        var existsCount = existsCount
        var fileName = originalFileName
        if (existsCount > 0) {
            val pos = originalFileName.lastIndexOf('.')
            val ext = if (pos < 0) "" else originalFileName.substring(pos)
            val name = originalFileName.substring(0, originalFileName.length - ext.length)
            fileName = String.format("%s(%d)%s", name, existsCount, ext)
        }
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        if (file.exists()) {
            return saveFileToDownloadFolder(context, bytes, originalFileName, ++existsCount)
        }
        try {
            FileOutputStream(file).use { outputStream -> outputStream.write(bytes) }
        } catch (e: FileNotFoundException) {
            Timber.e(e)
        }
        return file
    }

    fun completeFileDownload(file: File, context: Context, description: String?) {
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager?.addCompletedDownload(
            file.name, description, true, getMimeType(file),
            file.absolutePath, file.length(), true
        )
    }

    private fun cleanUpFileName(fileName: String): String {
        return fileName.replace(":".toRegex(), " ")
    }

    private fun getMimeType(file: File): String? {
        val extension = getExtension(file.name)
        return if (extension != null && extension.length > 0) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1))
        } else "application/octet-stream"
    }

    private fun getExtension(uri: String?): String? {
        if (uri == null) {
            return null
        }
        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            uri.substring(dot)
        } else {
            ""
        }
    }
}