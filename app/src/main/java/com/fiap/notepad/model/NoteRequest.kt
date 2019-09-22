package com.fiap.notepad.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteRequest(
    @SerializedName("note") val note: String = ""
): Parcelable