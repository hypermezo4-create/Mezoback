package com.moveos.mezoback.data

enum class JobStatus { Idle, Running, Success, Failed }

data class QuickAction(
    val title: String,
    val subtitle: String,
)

data class BuildJob(
    val title: String,
    val description: String,
    val status: JobStatus,
)

data class LiveLog(
    val time: String,
    val line: String,
)
