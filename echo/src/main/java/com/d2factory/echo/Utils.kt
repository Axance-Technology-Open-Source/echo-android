package com.d2factory.echo

import android.content.Context
import okhttp3.Request
import java.io.File

internal object Utils {

    fun getInternalAppMockFile(context: Context, request: Request): File {
        val baseFolder = getInternalAppMockDir(context)
        return File(baseFolder, getMockFileNameFromRequest(request))
    }

    private fun getInternalAppMockDir(context: Context): File {
        val baseFolderPath = context.filesDir.path +
                File.separator +
                context.getString(R.string.app_name) +
                File.separator +
                EchoConfig.bundleMockFolder

        val baseFolder = File(baseFolderPath)

        if (!baseFolder.exists()) {
            baseFolder.mkdirs()
        }
        return baseFolder
    }

    fun getAssetMockFilePath(request: Request): String {
        val mockFileName = getMockFileNameFromRequest(request)
        return "${EchoConfig.bundleName}${EchoConfig.mockFolderName}/$mockFileName"
    }

    private fun getMockFileNameFromRequest(request: Request): String {
        val pathSegments = request.url().pathSegments()
        val httpMethod = request.method()

        var fileName = httpMethod.toUpperCase()
        pathSegments.forEach {
            fileName = "$fileName-$it"
        }

        return "$fileName.json"
    }
}