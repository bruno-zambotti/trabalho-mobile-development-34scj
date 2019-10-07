package com.fiap.notepad.model

import com.google.gson.annotations.SerializedName

data class NoteResponseItem(
    @SerializedName("id") val id: Long,
    @SerializedName("note") val note: String
)