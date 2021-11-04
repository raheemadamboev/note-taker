package xyz.teamgravity.notetaker.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.teamgravity.notetaker.feature_note.presentation.MainActivity
import xyz.teamgravity.notetaker.feature_note.presentation.add_edit_note.AddEditNoteScreen
import xyz.teamgravity.notetaker.feature_note.presentation.util.Screen
import xyz.teamgravity.notetaker.injection.ApplicationModule
import xyz.teamgravity.notetaker.ui.theme.NoteTakerTheme

@HiltAndroidTest
@UninstallModules(ApplicationModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            NoteTakerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }

                        composable(route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )) {
                            val noteColor = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = noteColor
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        composeRule.apply {
            onNodeWithContentDescription("add").performClick()

            onNodeWithTag("title_edit_text").performTextInput("test-title")
            onNodeWithTag("content_edit_text").performTextInput("test-content")
            onNodeWithContentDescription("save").performClick()

            onNodeWithText("test-title").assertIsDisplayed()
            onNodeWithText("test-title").performClick()

            onNodeWithTag("title_edit_text").assertTextEquals("test-title")
            onNodeWithTag("content_edit_text").assertTextEquals("test-content")

            onNodeWithTag("title_edit_text").performTextInput("-2")
            onNodeWithContentDescription("save").performClick()

            onNodeWithText("test-title-2").assertIsDisplayed()
        }
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        composeRule.apply {
            for (i in 1..3) {
                onNodeWithContentDescription("add").performClick()

                onNodeWithTag("title_edit_text").performTextInput(i.toString())
                onNodeWithTag("content_edit_text").performTextInput(i.toString())
                onNodeWithContentDescription("save").performClick()
            }

            for (i in 1..3) {
                onNodeWithText(i.toString()).assertIsDisplayed()
            }

            onNodeWithContentDescription("sort").performClick()
            onNodeWithContentDescription("Title").performClick()
            onNodeWithContentDescription("Descending").performClick()

            onAllNodesWithTag("note_card")[0].assertTextContains("3")
            onAllNodesWithTag("note_card")[1].assertTextContains("2")
            onAllNodesWithTag("note_card")[2].assertTextContains("1")
        }
    }
}