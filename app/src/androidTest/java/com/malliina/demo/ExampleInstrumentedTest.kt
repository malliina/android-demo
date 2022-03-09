package com.malliina.demo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.malliina.demo", appContext.packageName)
  }

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun uiTest() {
    val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as DemoApp
    val lang = Lang.english
    composeTestRule.apply {
      setContent {
        MainScreen(viewModel = DemoViewModel(app), lang)
      }
      onNodeWithText(lang.title).assertIsDisplayed()
//      onNodeWithText(lang.conversations.demo).assertIsDisplayed()
      onNodeWithText(lang.conversations.tap).performClick()
      val author = SampleData.messages.first().author
      onAllNodesWithText(author).onFirst().assertIsDisplayed()
    }
  }
}
