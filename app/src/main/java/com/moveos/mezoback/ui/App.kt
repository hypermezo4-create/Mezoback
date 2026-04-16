package com.moveos.mezoback.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moveos.mezoback.data.BuildJob
import com.moveos.mezoback.data.JobStatus
import com.moveos.mezoback.data.LiveLog
import com.moveos.mezoback.data.QuickAction
import com.moveos.mezoback.ui.theme.CyberBlue
import com.moveos.mezoback.ui.theme.CyberPurple
import com.moveos.mezoback.ui.theme.GlowSurface

private enum class Tab { Dashboard, Jobs, Logs, Settings }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MEZOBackApp(vm: MainViewModel) {
    var currentTab by remember { mutableStateOf(Tab.Dashboard) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF070A16), Color(0xFF10051B)))),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("MEZOBack", fontWeight = FontWeight.Bold)
                        Text("MoveOS visual identity", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
                listOf(
                    Tab.Dashboard to Icons.Outlined.Bolt,
                    Tab.Jobs to Icons.Outlined.Build,
                    Tab.Logs to Icons.Outlined.Description,
                    Tab.Settings to Icons.Outlined.Settings,
                ).forEach { (tab, icon) ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        icon = { androidx.compose.material3.Icon(icon, contentDescription = tab.name) },
                        label = { Text(tab.name) }
                    )
                }
            }
        }
    ) { padding ->
        when (currentTab) {
            Tab.Dashboard -> DashboardScreen(padding, vm)
            Tab.Jobs -> JobsScreen(padding, vm)
            Tab.Logs -> LogsScreen(padding, vm)
            Tab.Settings -> SettingsScreen(padding)
        }
    }
}

@Composable
private fun DashboardScreen(padding: PaddingValues, vm: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            GlowCard {
                Text("Current Project", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                Text(vm.currentProject, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("DNA-style workspace flow with MoveOS cyber theme.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                LinearProgressIndicator(
                    progress = { vm.progress / 100f },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                )
                Text("Build progress: ${vm.progress}%", modifier = Modifier.padding(top = 8.dp))
            }
        }
        item {
            Text("Quick Actions", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
        items(vm.actions) { action -> QuickActionCard(action) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = vm::simulateSuccess, modifier = Modifier.weight(1f)) { Text("Simulate Success") }
                OutlinedButton(onClick = vm::simulateFailure, modifier = Modifier.weight(1f)) { Text("Simulate Error") }
            }
        }
    }
}

@Composable
private fun JobsScreen(padding: PaddingValues, vm: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("One-Click Build Pipeline", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text("Import ROM → unpack → patch → repack → super → cleanup → report", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(vm.jobs) { job -> JobCard(job) }
    }
}

@Composable
private fun LogsScreen(padding: PaddingValues, vm: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Live Report", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text("Clear step-by-step status and failure reasons.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        items(vm.logs) { log -> LogCard(log) }
    }
}

@Composable
private fun SettingsScreen(padding: PaddingValues) {
    Column(
        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlowCard {
            Text("Theme", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text("MoveOS dark neon blue/purple identity is the default.")
        }
        GlowCard {
            Text("Planned Real Integrations", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Text(
                """• Project import
• Script runner
• Partition tools
• Temp cleanup
• Report export"""
            )
        }
    }
}

@Composable
private fun QuickActionCard(action: QuickAction) {
    GlowCard {
        Text(action.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(action.subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun JobCard(job: BuildJob) {
    val accent = when (job.status) {
        JobStatus.Idle -> MaterialTheme.colorScheme.outline
        JobStatus.Running -> CyberBlue
        JobStatus.Success -> Color(0xFF49E68A)
        JobStatus.Failed -> Color(0xFFFF5E7A)
    }
    GlowCard(accent = accent) {
        Text(job.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(job.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Status: ${job.status}", color = accent, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun LogCard(log: LiveLog) {
    GlowCard(accent = CyberPurple) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(log.time, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text("  ${log.line}", color = Color.White)
        }
    }
}

@Composable
private fun GlowCard(accent: Color = GlowSurface, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, accent.copy(alpha = 0.65f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { content() }
        }
    }
}
