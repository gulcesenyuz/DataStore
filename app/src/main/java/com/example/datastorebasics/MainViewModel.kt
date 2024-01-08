package com.example.datastorebasics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var name: String by mutableStateOf("")
    var job: String by mutableStateOf("")


}