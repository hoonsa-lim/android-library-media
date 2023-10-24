package com.anythingsskyblue.android.library.media.metadata

import java.time.LocalDateTime
import kotlin.time.Duration

data class VideoMetadata(
    val duration: Duration? = null,
    val date: LocalDateTime? = null,
    val width: Int? = null,
    val height: Int? = null,
)
