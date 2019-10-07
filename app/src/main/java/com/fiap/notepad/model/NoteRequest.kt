package com.fiap.notepad.model

import com.google.gson.annotations.SerializedName

data class NoteRequest(
    @SerializedName("note") val note: String = ""
)