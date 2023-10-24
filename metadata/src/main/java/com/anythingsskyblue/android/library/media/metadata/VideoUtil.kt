package com.anythingsskyblue.android.library.media.metadata

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object VideoUtil {

    suspend fun getFirstFrame(
        webVideoPath: String,
        headers: Map<String, String> = emptyMap()
    ): Result<Bitmap> {
        val mediaMetadataRetriever = MediaMetadataRetriever()

        return try {
            mediaMetadataRetriever.setDataSource(webVideoPath, headers)

            val bitmap = mediaMetadataRetriever
                .getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST)
                ?: throw Exception("getFrameAtTime is null")

            Result.success(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        } finally {
            try { mediaMetadataRetriever.release() }
            catch (ignore: Exception){ }
        }
    }

    suspend fun getFirstFrame(
        context: Context,
        uri: Uri,
    ): Result<Bitmap> {
        val mediaMetadataRetriever = MediaMetadataRetriever()

        return try {
            mediaMetadataRetriever.setDataSource(context, uri)

            val bitmap = mediaMetadataRetriever
                .getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST)
                ?: throw Exception("getFrameAtTime is null")

            Result.success(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        } finally {
            try { mediaMetadataRetriever.release() }
            catch (ignore: Exception){ }
        }
    }

    suspend fun getFramesByIndex(
        webVideoPath: String,
        startIndex: Int,
        count: Int,
        headers: Map<String, String> = emptyMap()
    ): Result<List<Bitmap>> {
        val mediaMetadataRetriever = MediaMetadataRetriever()

        return try {
            mediaMetadataRetriever.setDataSource(webVideoPath, headers)
            val bitmaps = mediaMetadataRetriever.getFramesAtIndex(startIndex, count)

            Result.success(bitmaps)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        } finally {
            try { mediaMetadataRetriever.release() }
            catch (ignore: Exception){ }
        }
    }

    suspend fun getMetadata(
        webVideoPath: String,
        headers: Map<String, String> = emptyMap(),
    ): Result<VideoMetadata>{
        val retriever = MediaMetadataRetriever()

        return try {
            retriever.setDataSource(webVideoPath, headers)

            val data = VideoMetadata(
                duration = retriever.extractDurationOrNull(),
                date = retriever.extractDateOrNull(),
                width = retriever.extractWidthOrNull(),
                height = retriever.extractHeightOrNull(),
            )

            Result.success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        } finally {
            try { retriever.release() }
            catch (ignore: Exception){ }
        }
    }

    suspend fun Context.saveBitmapToGallery(
        bitmap: Bitmap,
    ): Result<Unit> {
        val time = System.currentTimeMillis()
        var outputStream: OutputStream? = null
        var exception: Exception?

        try {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, time)
                put(MediaStore.Images.Media.DESCRIPTION, "")
                put(MediaStore.Images.Media.DATE_ADDED, time)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.ORIENTATION, 0)
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }

            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                ?: throw Exception("insert fail.")

            outputStream = contentResolver.openOutputStream(uri)
                ?: throw Exception("output stream fail.")

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()

            return Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            exception = e
        } finally {
            try { outputStream?.close() }
            catch (ignore: Exception){}
        }

        return Result.failure(exception ?: Exception("fail"))
    }
}

private fun MediaMetadataRetriever.extractWidthOrNull(): Int? {
    return this.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull()
}

private fun MediaMetadataRetriever.extractHeightOrNull(): Int? {
    return this.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull()
}

private fun MediaMetadataRetriever.extractDateOrNull(): LocalDateTime? {
    val text = this.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)
    val format = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSS'Z'")
    return LocalDateTime.parse(text, format)
}

private fun MediaMetadataRetriever.extractDurationOrNull(): Duration? {
    return this.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        ?.toLongOrNull()
        ?.toDuration(DurationUnit.MILLISECONDS)
}
