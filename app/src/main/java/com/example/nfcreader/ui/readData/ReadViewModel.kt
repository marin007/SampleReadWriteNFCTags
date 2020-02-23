package com.example.nfcreader.ui.readData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReadViewModel : ViewModel() {

    private val _tag = MutableLiveData<String>().apply {
        value = ""
    }

    val tag: LiveData<String> = _tag

    fun setTagMessage(message: String) {
        _tag.value = message
    }
}