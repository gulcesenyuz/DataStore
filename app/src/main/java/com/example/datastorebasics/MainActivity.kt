package com.example.datastorebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastorebasics.ui.theme.DataStoreBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreBasicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        GreetingText(name = "Developer")
                        NameInputScreen()
                    }

                }
            }
        }
    }
}


@Composable
fun GreetingText(name: String) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello $name!",
            style = TextStyle(
                fontSize = 24.sp, // Specifying font size as 24sp
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataStoreBasicsTheme {
        GreetingText("Developer")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputScreen() {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enter your name:")
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            placeholder = { Text("Your name") },
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun NameInputScreenPreview() {
    NameInputScreen()
}