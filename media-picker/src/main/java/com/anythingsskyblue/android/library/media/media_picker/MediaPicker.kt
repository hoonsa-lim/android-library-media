package com.anythingsskyblue.android.library.media.media_picker

import android.content.Context
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.anythingsskyblue.android.library.ui.common.ContextUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
object MediaPicker {
    private const val KEY_IMAGE_PICKER = "show_image_picker"
    private const val KEY_VIDEO_PICKER = "show_video_picker"

    suspend fun showImagePicker(context: Context): Uri? = suspendCancellableCoroutine{ continuation ->
        val activity = ContextUtil.findComponentActivity(context)
        val callback : (Uri?) -> Unit = { uri -> continuation.resume(uri) }

        val pickMedia = activity
            .activityResultRegistry
            .register(KEY_IMAGE_PICKER, ActivityResultContracts.PickVisualMedia(), callback)

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    suspend fun showVideoPicker(context: Context): Uri? = suspendCancellableCoroutine{ continuation ->
        val activity = ContextUtil.findComponentActivity(context)
        val callback : (Uri?) -> Unit = { uri -> continuation.resume(uri) }

        val pickMedia = activity
            .activityResultRegistry
            .register(KEY_VIDEO_PICKER, ActivityResultContracts.PickVisualMedia(), callback)

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
    }
}