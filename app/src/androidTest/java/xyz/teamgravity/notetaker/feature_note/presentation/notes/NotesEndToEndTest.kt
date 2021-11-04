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
        composeRule.onNodeWithContentDescription("add").performClick()

        composeRule.onNodeWithTag("title_edit_text").performTextInput("test-title")
        composeRule.onNodeWithTag("content_edit_text").performTextInput("test-content")
        composeRule.onNodeWithContentDescription("save").performClick()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag("title_edit_text").assertTextEquals("test-title")
        composeRule.onNodeWithTag("content_edit_text").assertTextEquals("test-content")

        composeRule.onNodeWithTag("title_edit_text").performTextInput("-2")
        composeRule.onNodeWithContentDescription("save").performClick()

        composeRule.onNodeWithText("test-title-2").assertIsDisplayed()
    }
}