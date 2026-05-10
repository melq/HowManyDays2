package com.github.melq.howmanydays

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appStartsAndDisplaysMainTitle() {
        // アプリ起動時にトップバーのタイトル「HowManyDays」が表示されることを確認
        composeTestRule.onNodeWithText("HowManyDays").assertExists()
    }

    @Test
    fun navigateToSettingsScreen() {
        // 設定アイコン（contentDescription="Settings"）をクリック
        composeTestRule.onNodeWithContentDescription("Settings").performClick()

        // 設定画面に遷移後、「設定」や「通知の設定」というテキストが表示されることを確認
        composeTestRule.onNodeWithText("設定").assertExists()
        composeTestRule.onNodeWithText("通知の設定").assertExists()
    }
}
