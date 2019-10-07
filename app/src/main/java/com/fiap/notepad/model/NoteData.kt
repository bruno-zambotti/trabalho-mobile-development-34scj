package com.fiap.notepad.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteData(
    var id: Long = 0,
    var note: String = ""
): Parcelable