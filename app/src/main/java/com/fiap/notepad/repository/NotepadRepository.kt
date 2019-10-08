package com.fiap.notepad.repository

import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.model.NoteResponseItem

interface NotepadRepository {
    fun getNotes(
        onComplete:(List<NoteResponseItem>?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun updateNote(
        noteId: Int,
        noteRequest: NoteRequest,
        onComplete: (NoteResponseItem?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun createNote(
        noteRequest: NoteRequest,
        onComplete: (NoteResponseItem?) -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun deleteNote(
        noteId: Int,
        onComplete: () -> Unit,
        onError: (Throwable?) -> Unit
    )
}