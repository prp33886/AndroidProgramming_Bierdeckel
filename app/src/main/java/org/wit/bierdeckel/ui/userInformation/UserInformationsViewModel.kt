package org.wit.bierdeckel.ui.userInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInformationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Test test tes"
    }

    val text: LiveData<String> = _text


}