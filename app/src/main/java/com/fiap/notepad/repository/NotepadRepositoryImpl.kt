package com.fiap.notepad.repository

import com.fiap.notepad.model.NoteResponseItem
import com.fiap.notepad.api.NotepadService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteRequest

class NotepadRepositoryImpl(var notepadService: NotepadService) : NotepadRepository {
    override fun updateNote(
        noteId: Int,
        noteRequest: NoteRequest,
        onComplete: (NoteResponseItem?) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        notepadService.updateNote(noteId, noteRequest)
            .enqueue(object : Callback<NoteResponseItem> {
                override fun onFailure(call: Call<NoteResponseItem>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<NoteResponseItem>, response: Response<NoteResponseItem>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable(R.string.repository_notepad_error_when_update.toString()))
                    }
                }
            })
    }

    override fun createNote(
        noteRequest: NoteRequest,
        onComplete: (NoteResponseItem?) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        notepadService.postNote(noteRequest)
            .enqueue(object : Callback<NoteResponseItem> {
                override fun onFailure(call: Call<NoteResponseItem>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<NoteResponseItem>, response: Response<NoteResponseItem>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable(R.string.repository_notepad_error_when_create.toString()))
                    }
                }
            })
    }

    override fun deleteNote(noteId: Int, onComplete: () -> Unit, onError: (Throwable?) -> Unit) {
        notepadService.deleteNote(noteId)
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful) {
                        onComplete()
                    } else {
                        onError(Throwable(R.string.repository_notepad_error_when_delete.toString()))
                    }
                }
            })
    }

    override fun getNotes(
        onComplete: (List<NoteResponseItem>?) -> Unit,
        onError: (Throwable?) -> Unit) {
        notepadService.getNotes().enqueue(object: Callback<List<NoteResponseItem>> {
            override fun onFailure(call: Call<List<NoteResponseItem>>, t: Throwable) {
                onError(t)
            }
            override fun onResponse(call:  Call<List<NoteResponseItem>>, response: Response<List<NoteResponseItem>>) {
                if (response.isSuccessful) {
                    onComplete(response.body())
                } else {
                    onError(Throwable(R.string.repository_notepad_error_when_get.toString()))
                }
            }
        })
    }
}