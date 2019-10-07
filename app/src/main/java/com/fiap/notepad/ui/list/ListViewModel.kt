package com.fiap.notepad.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.model.NoteResponseItem
import com.fiap.notepad.repository.NotepadRepository

class ListViewModel (val notepadRepository: NotepadRepository) : ViewModel() {

    val messageResponse: MutableLiveData<String> = MutableLiveData()
    val notes: MutableLiveData<List<NoteResponseItem>> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getNotes() {
        isLoading.value = true
        notepadRepository.getNotes({
                notes.value = it
                messageResponse.value = ""
                isLoading.value = false
            }, {
                notes.value = emptyList()
                messageResponse.value = it?.message
                isLoading.value = false
            }
        )
    }

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

    fun updateNote(noteId : Long, noteRequest : NoteRequest) {
        isLoading.value = true
        notepadRepository.updateNote(noteId, noteRequest, {
            isLoading.value = false
            messageResponse.value = R.string.view_model_form_update_successfully.toString()
        }, {
            isLoading.value = false
            messageResponse.value = it?.message
        })
    }

    fun deleteNote(noteId : Long) {
        isLoading.value = true
        notepadRepository.deleteNote(noteId, {
            isLoading.value = false
            messageResponse.value = R.string.view_model_form_delete_successfully.toString()
        }, {
            isLoading.value = false
        })
    }
}
