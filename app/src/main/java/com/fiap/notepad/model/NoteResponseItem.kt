package com.fiap.notepad.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteResponseItem(
    @SerializedName("id") val id: Long,
    @SerializedName("note") val note: String
): Parcelable