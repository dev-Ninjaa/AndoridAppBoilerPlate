package com.example

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuraTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AuraApp()
    }
  }
}

data class DailyIntention(
  val id: Int,
  val text: String,
  val completed: Boolean = false,
)

data class MindfulNote(
  val id: Long,
  val text: String,
  val tag: String,
  val timestamp: String,
)

private val INSPIRATION_QUOTES = listOf(
  "Notice the quiet beauty in small moments of today." to "Mindful Presence",
  "Focus is not about doing more; it is about doing what truly matters." to "Clarity",
  "Take a deep, gentle breath. You are grounded, capable, and ready." to "Serenity",
  "Simplify the path ahead. One thoughtful step at a time." to "Simplicity",
  "Energy flows where intentional attention goes." to "Intention",
  "Calm is a superpower you create from within." to "Inner Peace",
  "Celebrate the quiet progress nobody else sees." to "Growth"
)

private val MOOD_TAGS = listOf("🌿 Calm", "✨ Focus", "💡 Idea", "🎯 Priority", "🙏 Gratitude")

@Composable
fun AuraApp() {
  val systemDark = isSystemInDarkTheme()
  var isDarkTheme by remember { mutableStateOf(systemDark) }

  AuraTheme(darkTheme = isDarkTheme) {
    AuraMainScreen(
      isDarkTheme = isDarkTheme,
      onToggleTheme = { isDarkTheme = !isDarkTheme }
    )
  }
}

@Composable
fun AuraMainScreen(
  isDarkTheme: Boolean,
  onToggleTheme: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var quoteIndex by remember { mutableIntStateOf(0) }
  var rotationDegrees by remember { mutableFloatStateOf(0f) }
  val animatedRotation by animateFloatAsState(
    targetValue = rotationDegrees,
    animationSpec = spring(),
    label = "quote_rotation"
  )

  val intentions = remember {
    mutableStateListOf(
      DailyIntention(1, "Take 3 deep, restorative breaths before starting", true),
      DailyIntention(2, "Complete 45 minutes of distraction-free deep work", false),
      DailyIntention(3, "Step outside or pause to savor natural light", false)
    )
  }

  var newIntentionText by remember { mutableStateOf("") }
  var showAddIntention by remember { mutableStateOf(false) }

  val notes = remember {
    mutableStateListOf(
      MindfulNote(
        id = 1L,
        text = "Design with intention. Clean negative space gives room for thoughts to breathe.",
        tag = "✨ Focus",
        timestamp = "09:15 AM"
      )
    )
  }

  var currentNoteText by remember { mutableStateOf("") }
  var selectedTag by remember { mutableStateOf(MOOD_TAGS[0]) }
  var showApkGuideDialog by remember { mutableStateOf(false) }

  val currentGreeting = remember {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    when {
      hour < 12 -> "Good morning"
      hour < 18 -> "Good afternoon"
      else -> "Peaceful evening"
    }
  }

  val formattedDate = remember {
    val sdf = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    sdf.format(Date())
  }

  val completedCount = intentions.count { it.completed }
  val progress = if (intentions.isNotEmpty()) completedCount.toFloat() / intentions.size else 0f

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets(0, 0, 0, 0)
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        // Top Spacing & Header
        item {
          Spacer(modifier = Modifier.height(48.dp))
          HeaderSection(
            greeting = currentGreeting,
            date = formattedDate,
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme,
            onShowApkGuide = { showApkGuideDialog = true }
          )
        }

        // Daily Inspiration Card
        item {
          val currentQuote = INSPIRATION_QUOTES[quoteIndex % INSPIRATION_QUOTES.size]
          DailyInspirationCard(
            quote = currentQuote.first,
            author = currentQuote.second,
            rotation = animatedRotation,
            onShuffle = {
              quoteIndex++
              rotationDegrees += 360f
            }
          )
        }

        // Daily Intentions Section
        item {
          IntentionsSection(
            intentions = intentions,
            completedCount = completedCount,
            progress = progress,
            showAddIntention = showAddIntention,
            newIntentionText = newIntentionText,
            onNewIntentionTextChange = { newIntentionText = it },
            onToggleAdd = { showAddIntention = !showAddIntention },
            onAddIntention = {
              if (newIntentionText.isNotBlank()) {
                intentions.add(DailyIntention(intentions.size + 1, newIntentionText.trim()))
                newIntentionText = ""
                showAddIntention = false
              }
            },
            onToggleIntention = { id ->
              val index = intentions.indexOfFirst { it.id == id }
              if (index != -1) {
                intentions[index] = intentions[index].copy(completed = !intentions[index].completed)
              }
            },
            onDeleteIntention = { id ->
              intentions.removeAll { it.id == id }
            }
          )
        }

        // Mindful Scratchpad Section
        item {
          ScratchpadSection(
            currentText = currentNoteText,
            selectedTag = selectedTag,
            tags = MOOD_TAGS,
            onTextChange = { currentNoteText = it },
            onTagSelect = { selectedTag = it },
            onSaveNote = {
              if (currentNoteText.isNotBlank()) {
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                notes.add(
                  0,
                  MindfulNote(
                    id = System.currentTimeMillis(),
                    text = currentNoteText.trim(),
                    tag = selectedTag,
                    timestamp = timeFormat.format(Date())
                  )
                )
                currentNoteText = ""
              }
            }
          )
        }

        // Saved Notes List
        items(notes, key = { it.id }) { note ->
          NoteCard(
            note = note,
            onDelete = { notes.removeAll { it.id == note.id } }
          )
        }

        // GitHub Workflow & APK Testing Card
        item {
          ApkWorkflowCard(
            onOpenGuide = { showApkGuideDialog = true }
          )
        }

        // Bottom Insets spacing
        item {
          Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }

    if (showApkGuideDialog) {
      ApkGuideDialog(onDismiss = { showApkGuideDialog = false })
    }
  }
}

@Composable
private fun HeaderSection(
  greeting: String,
  date: String,
  isDarkTheme: Boolean,
  onToggleTheme: () -> Unit,
  onShowApkGuide: () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
        )
        Text(
          text = "AURA",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
          letterSpacing = 2.sp,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = greeting,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = date,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
      IconButton(
        onClick = onShowApkGuide,
        modifier = Modifier.testTag("apk_info_button")
      ) {
        Icon(
          imageVector = Icons.Default.Smartphone,
          contentDescription = "APK Testing Info",
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      IconButton(
        onClick = onToggleTheme,
        modifier = Modifier.testTag("theme_toggle_button")
      ) {
        Icon(
          imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
          contentDescription = "Toggle Theme Mode",
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
private fun DailyInspirationCard(
  quote: String,
  author: String,
  rotation: Float,
  onShuffle: () -> Unit,
) {
  val gradient = Brush.linearGradient(
    colors = listOf(
      MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
      MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f),
      MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f)
    )
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        shape = RoundedCornerShape(24.dp)
      ),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(gradient)
        .padding(24.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Spa,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "DAILY REFLECTION",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.primary,
              letterSpacing = 1.5.sp,
              fontWeight = FontWeight.Bold
            )
          }

          IconButton(
            onClick = onShuffle,
            modifier = Modifier
              .size(36.dp)
              .testTag("shuffle_quote_button")
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = "Shuffle reflection quote",
              modifier = Modifier
                .size(20.dp)
                .rotate(rotation),
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "“$quote”",
          style = MaterialTheme.typography.titleLarge,
          color = MaterialTheme.colorScheme.onSurface,
          lineHeight = 30.sp,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "— $author",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.Normal
        )
      }
    }
  }
}

@Composable
private fun IntentionsSection(
  intentions: List<DailyIntention>,
  completedCount: Int,
  progress: Float,
  showAddIntention: Boolean,
  newIntentionText: String,
  onNewIntentionTextChange: (String) -> Unit,
  onToggleAdd: () -> Unit,
  onAddIntention: () -> Unit,
  onToggleIntention: (Int) -> Unit,
  onDeleteIntention: (Int) -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
        shape = RoundedCornerShape(24.dp)
      ),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(modifier = Modifier.padding(20.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Today's Intentions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = "$completedCount of ${intentions.size} fulfilled",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        IconButton(
          onClick = onToggleAdd,
          modifier = Modifier.testTag("add_intention_button")
        ) {
          Icon(
            imageVector = if (showAddIntention) Icons.Default.Close else Icons.Default.Add,
            contentDescription = if (showAddIntention) "Cancel" else "Add Intention",
            tint = MaterialTheme.colorScheme.primary
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(CircleShape),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
      )

      AnimatedVisibility(
        visible = showAddIntention,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Column(modifier = Modifier.padding(top = 16.dp)) {
          OutlinedTextField(
            value = newIntentionText,
            onValueChange = onNewIntentionTextChange,
            placeholder = { Text("e.g., Read 10 pages with tea") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("new_intention_input"),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MaterialTheme.colorScheme.primary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
          )
          Spacer(modifier = Modifier.height(8.dp))
          Button(
            onClick = onAddIntention,
            modifier = Modifier
              .align(Alignment.End)
              .testTag("save_intention_button"),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text("Add Intention", color = MaterialTheme.colorScheme.onPrimary)
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        intentions.forEach { item ->
          val textColor by animateColorAsState(
            targetValue = if (item.completed) {
              MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            } else {
              MaterialTheme.colorScheme.onSurface
            },
            label = "text_color"
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .clickable { onToggleIntention(item.id) }
              .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              Icon(
                imageVector = if (item.completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = if (item.completed) "Completed" else "Incomplete",
                tint = if (item.completed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline,
                modifier = Modifier
                  .size(24.dp)
                  .testTag("intention_checkbox_${item.id}")
              )
              Text(
                text = item.text,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textDecoration = if (item.completed) TextDecoration.LineThrough else TextDecoration.None
              )
            }

            IconButton(
              onClick = { onDeleteIntention(item.id) },
              modifier = Modifier.size(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ScratchpadSection(
  currentText: String,
  selectedTag: String,
  tags: List<String>,
  onTextChange: (String) -> Unit,
  onTagSelect: (String) -> Unit,
  onSaveNote: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
        shape = RoundedCornerShape(24.dp)
      ),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(modifier = Modifier.padding(20.dp)) {
      Text(
        text = "Mindful Scratchpad",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold
      )
      Text(
        text = "Jot down a fleeting thought, insight, or gratitude.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        tags.forEach { tag ->
          val isSelected = tag == selectedTag
          FilterChip(
            selected = isSelected,
            onClick = { onTagSelect(tag) },
            label = { Text(tag, style = MaterialTheme.typography.labelSmall) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
              selectedLabelColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = currentText,
        onValueChange = onTextChange,
        placeholder = { Text("What is currently on your mind?") },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("scratchpad_input"),
        minLines = 3,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = MaterialTheme.colorScheme.primary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Button(
          onClick = onSaveNote,
          enabled = currentText.isNotBlank(),
          modifier = Modifier.testTag("save_note_button"),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("Save Reflection")
        }
      }
    }
  }
}

@Composable
private fun NoteCard(
  note: MindfulNote,
  onDelete: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        shape = RoundedCornerShape(20.dp)
      ),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.surface,
          shadowElevation = 0.5.dp
        ) {
          Text(
            text = note.tag,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontWeight = FontWeight.Medium
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = note.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
          )
          IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
            Icon(
              imageVector = Icons.Default.DeleteOutline,
              contentDescription = "Delete note",
              tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = note.text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        lineHeight = 22.sp
      )
    }
  }
}

@Composable
private fun ApkWorkflowCard(
  onOpenGuide: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .widthIn(max = 600.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f),
        shape = RoundedCornerShape(24.dp)
      ),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f)
    )
  ) {
    Column(modifier = Modifier.padding(20.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Smartphone,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.secondary,
              modifier = Modifier.size(20.dp)
            )
          }

          Column {
            Text(
              text = "Device APK Pipeline",
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "Automated GitHub CI/CD Active",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.Medium
            )
          }
        }

        IconButton(onClick = onOpenGuide) {
          Icon(
            imageVector = Icons.Default.HelpOutline,
            contentDescription = "APK Instructions",
            tint = MaterialTheme.colorScheme.secondary
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Push any code to GitHub to trigger your automated Gradle workflow. The signed APK artifact is published instantly for download and testing directly on your Android phone.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Package: com.aistudio.aura.qzxkwv",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
          )
          Text(
            text = "Target: Android 14+ / SDK 36 (Debug Signed)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
          )
        }

        Button(
          onClick = onOpenGuide,
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text("How to Install", color = MaterialTheme.colorScheme.onSecondary, fontSize = 13.sp)
        }
      }
    }
  }
}

@Composable
private fun ApkGuideDialog(onDismiss: () -> Unit) {
  AlertDialog(
    onDismissRequest = onDismiss,
    modifier = Modifier.testTag("apk_guide_dialog"),
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Smartphone,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary
        )
        Text("Install APK on Your Phone", style = MaterialTheme.typography.titleLarge)
      }
    },
    text = {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 8.dp)
      ) {
        InstructionStep(
          step = "1",
          title = "Push code to GitHub",
          desc = "Commit and push to main or master branch. The GitHub Action will start building automatically."
        )
        InstructionStep(
          step = "2",
          title = "Open GitHub Actions Tab",
          desc = "In your browser, click 'Actions' -> 'Build and Deliver Android APK' -> view the latest run."
        )
        InstructionStep(
          step = "3",
          title = "Download Artifact",
          desc = "Scroll to the bottom of the page and click 'Aura-Debug-APK' to download the ZIP file."
        )
        InstructionStep(
          step = "4",
          title = "Install & Test on Device",
          desc = "Extract the ZIP on your phone, tap 'app-debug.apk', allow 'Install unknown apps' if prompted, and launch Aura!"
        )
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Got it", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
      }
    },
    shape = RoundedCornerShape(24.dp),
    containerColor = MaterialTheme.colorScheme.surface
  )
}

@Composable
private fun InstructionStep(step: String, title: String, desc: String) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primaryContainer),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = step,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
      )
    }
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = desc,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

