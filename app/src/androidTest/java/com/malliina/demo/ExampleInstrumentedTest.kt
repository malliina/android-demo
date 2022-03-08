package com.malliina.demo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

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
//  val testDispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

  @Test
  fun uiTest() {
    val lang = Lang.english
    composeTestRule.apply {
      setContent {
        DemoApp(viewModel = DemoViewModel(), lang)
      }
      onNodeWithText(lang.title).assertIsDisplayed()
      onNodeWithText(lang.conversations.demo).assertIsDisplayed()
      onNodeWithText(lang.conversations.tap).performClick()
      val author = SampleData.messages.first().author
      onAllNodesWithText(author).onFirst().assertIsDisplayed()
    }
  }
}
