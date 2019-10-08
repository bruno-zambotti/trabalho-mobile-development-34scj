package com.fiap.notepad.api

import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.model.NoteResponseItem
import retrofit2.Call
import retrofit2.http.*

interface NotepadService {
    @GET("/notes")
    fun getNotes(): Call<List<NoteResponseItem>>

    @POST("/notes")
    fun postNote(@Body note: NoteRequest): Call<NoteResponseItem>

    @PUT("/notes/{noteId}")
    fun updateNote(@Path("noteId") noteId: Int, @Body note: NoteRequest): Call<NoteResponseItem>

    @DELETE("/notes/{noteId}")
    fun deleteNote(@Path("noteId") noteId: Int): Call<Void>
}