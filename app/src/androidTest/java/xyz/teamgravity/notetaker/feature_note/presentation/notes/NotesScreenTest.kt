package xyz.teamgravity.notetaker.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.teamgravity.notetaker.feature_note.presentation.MainActivity
import xyz.teamgravity.notetaker.feature_note.presentation.util.Screen
import xyz.teamgravity.notetaker.injection.ApplicationModule
import xyz.teamgravity.notetaker.ui.theme.NoteTakerTheme

@HiltAndroidTest
@UninstallModules(ApplicationModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()

            NoteTakerTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag("order_section_test").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("sort").performClick()
        composeRule.onNodeWithTag("order_section_test").assertIsDisplayed()
    }
}