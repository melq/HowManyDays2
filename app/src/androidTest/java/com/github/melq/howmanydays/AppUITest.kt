package com.github.melq.howmanydays

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * アプリ全体の基本的なUIの表示や画面遷移を検証するUIテスト（インストルメンテーションテスト）です。
 * 今後の大規模なリファクタリングによる致命的なデグレ（画面が表示されない等）を防ぐことを目的としています。
 */
@RunWith(AndroidJUnit4::class)
class AppUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * アプリ起動時にトップバーのタイトル「HowManyDays」が正しく表示されることを確認します。
     */
    @Test
    fun appStartsAndDisplaysMainTitle() {
        composeTestRule.onNodeWithText("HowManyDays").assertExists()
    }

    /**
     * メイン画面から設定アイコンをタップし、設定画面へ正しく遷移できることを確認します。
     * 遷移後、設定画面特有のテキスト（「設定」「通知の設定」）が表示されることを検証します。
     */
    @Test
    fun navigateToSettingsScreen() {
        composeTestRule.onNodeWithContentDescription("Settings").performClick()

        composeTestRule.onNodeWithText("設定").assertExists()
        composeTestRule.onNodeWithText("通知の設定").assertExists()
    }
}
