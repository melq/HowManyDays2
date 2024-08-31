package com.github.melq.howmanydays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HowManyDaysTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Riku",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Text(text = message,
        fontSize = 100.sp,
        lineHeight = 116.sp)
    Text(text = from,
        fontSize = 36.sp)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HowManyDaysTheme {
        GreetingText("Happy Birthday Sam!", "From Emma")
    }
}