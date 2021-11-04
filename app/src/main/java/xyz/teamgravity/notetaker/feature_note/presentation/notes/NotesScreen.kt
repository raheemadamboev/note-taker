package xyz.teamgravity.notetaker.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import xyz.teamgravity.notetaker.feature_note.presentation.notes.components.NoteCard
import xyz.teamgravity.notetaker.feature_note.presentation.notes.components.OrderSection
import xyz.teamgravity.notetaker.feature_note.presentation.util.Screen

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewmodel: NotesViewModel = hiltViewModel()
) {
    val state = viewmodel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.h4
                )

                IconButton(
                    onClick = {
                        viewmodel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "sort"
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewmodel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteCard(
                        note = note,
                        cornerRadius = 10.dp,
                        cutCornerSize = 30.dp,
                        onClick = {
                            navController.navigate(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                        },
                        onDeleteClick = {
                            viewmodel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewmodel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}