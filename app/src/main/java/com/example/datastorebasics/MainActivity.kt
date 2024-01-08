package com.example.datastorebasics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.datastorebasics.ui.theme.DataStoreBasicsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        val context = LocalContext.current
        val viewModel = MainViewModel()
        val dataStore = PreferenceDataStore(context)
        val savedName = dataStore.getNameValueDataStore.collectAsState(initial = "")
        val savedJob = dataStore.getJobValueDataStore.collectAsState(initial = "")
        DataStoreBasicsTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    GreetingText(name = "Developer")
                    InputField(
                        "name",
                        "your name",
                        1,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        viewModel
                    )
                    InputField(
                        "job",
                        "your job",
                        2,
                        modifier = Modifier.padding(top = 16.dp),
                        viewModel
                    )
                    Row {
                        ClickableButton("save to DataStore", 1, viewModel, dataStore)
                        ClickableButton("get from DataStore", 2, viewModel, dataStore)
                    }
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Let me guess your job ${savedName.value}, are you ${savedJob.value}? :)"
                    )
                }

            }
        }
    }
}


@Composable
fun GreetingText(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
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

@Composable
fun InputField(
    fieldName: String,
    hint: String,
    field: Int,
    modifier: Modifier,
    viewModel: MainViewModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enter your $fieldName :")
        var inputText by remember { mutableStateOf(hint) }
        TextField(
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
        )
        DisplayDataFieldText(name = inputText)
        when (field) {
            1 -> viewModel.name = inputText
            2 -> viewModel.job = inputText
        }
    }
}

@Composable
fun DisplayDataFieldText(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Data: $name",
            style = TextStyle(
                fontSize = 16.sp, // Specifying font size as 24sp
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun ClickableButton(
    text: String,
    action: Int,
    viewModel: MainViewModel,
    preferenceDataStore: PreferenceDataStore
) {
    Button(
        onClick = { handleButtonAction(action, viewModel, preferenceDataStore) },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = text)
    }

}

private fun handleButtonAction(
    action: Int,
    viewModel: MainViewModel,
    dataStore: PreferenceDataStore
) {
    when (action) {
        1 -> setValueDataStore(viewModel, dataStore)
        2 -> getValueDataStore(dataStore)
    }
}

private fun getValueDataStore(dataStore: PreferenceDataStore) {
    CoroutineScope(Dispatchers.IO).launch {
        dataStore.getValueDataStore.collect {
            withContext(Dispatchers.Main) {
                Log.d("DataStore val: ", "user name: ${it.userName} , user job: ${it.userJob}")
            }
        }
    }
}

private fun setValueDataStore(viewModel: MainViewModel, preferenceDataStore: PreferenceDataStore) {
    CoroutineScope(Dispatchers.IO).launch {
        val userDetails = UserDetails(userName = viewModel.name, userJob = viewModel.job)
        preferenceDataStore.setValueDataStore(userDetails)
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayDataFieldTextPreview() {
    DataStoreBasicsTheme {
        GreetingText("Developer")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataStoreBasicsTheme {
        GreetingText("Developer")
    }
}


