package com.fiap.notepad.model

import com.google.gson.annotations.SerializedName

data class NoteResponse(
     @SerializedName("notes") val notes: List<NoteResponseItem>
)