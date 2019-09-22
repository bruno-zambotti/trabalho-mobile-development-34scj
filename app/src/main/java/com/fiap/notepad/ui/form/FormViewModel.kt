package com.fiap.notepad.ui.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.repository.NotepadRepository
import com.fiap.notepad.R

class FormViewModel (val notepadRepository: NotepadRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val messageResponse = MutableLiveData<String>()

    fun createNote(noteRequest : NoteRequest) {
        isLoading.value = true
        notepadRepository.createNote(noteRequest, {
            isLoading.value = false
            messageResponse.value = R.string.view_model_form_request_successfully.toString()
        }, {
            isLoading.value = false
            messageResponse.value = it?.message
        })

    }
}