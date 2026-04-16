package com.moveos.mezoback.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moveos.mezoback.data.BuildJob
import com.moveos.mezoback.data.JobStatus
import com.moveos.mezoback.data.LiveLog
import com.moveos.mezoback.data.QuickAction

class MainViewModel : ViewModel() {
    var currentProject by mutableStateOf("/sdcard/MEZOBack/Projects/MoveOS_Orange")
        private set

    var actions by mutableStateOf(
        listOf(
            QuickAction("Import ROM", "Pick ROM package and analyze structure"),
            QuickAction("Project Menu", "Open DNA-style workspace actions"),
            QuickAction("Start Full Build", "Run the one-click MoveOS pipeline"),
            QuickAction("Cleanup Temp", "Delete working files after success"),
        )
    )
        private set

    var jobs by mutableStateOf(
        listOf(
            BuildJob("Unpack Super", "Extract super and partitions", JobStatus.Success),
            BuildJob("Apply MoveOS Patch Set", "Run configured safe ROM edits", JobStatus.Running),
            BuildJob("Repack Partitions", "Rebuild changed targets", JobStatus.Idle),
            BuildJob("Rebuild Super", "Inject rebuilt partitions", JobStatus.Idle),
            BuildJob("Cleanup Temp", "Remove work files and keep report", JobStatus.Idle),
        )
    )
        private set

    var logs by mutableStateOf(
        listOf(
            LiveLog("10:32:15", "Workspace ready: MoveOS_Orange"),
            LiveLog("10:32:18", "Analyzing ROM layout..."),
            LiveLog("10:32:22", "super.img detected"),
            LiveLog("10:32:26", "Current step: Apply MoveOS Patch Set"),
            LiveLog("10:32:29", "No blocking errors"),
        )
    )
        private set

    var progress by mutableStateOf(42)
        private set

    fun simulateSuccess() {
        jobs = jobs.mapIndexed { index, job ->
            when {
                index < 5 -> job.copy(status = JobStatus.Success)
                else -> job
            }
        }
        progress = 100
        logs = logs + LiveLog("10:33:10", "Build completed successfully")
    }

    fun simulateFailure() {
        jobs = jobs.mapIndexed { index, job ->
            when (index) {
                1 -> job.copy(status = JobStatus.Failed)
                0 -> job.copy(status = JobStatus.Success)
                else -> job.copy(status = JobStatus.Idle)
            }
        }
        progress = 33
        logs = logs + LiveLog("10:33:10", "Failed: target size exceeded during patch stage")
    }
}
