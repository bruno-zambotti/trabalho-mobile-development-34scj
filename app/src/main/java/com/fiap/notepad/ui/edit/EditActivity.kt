package com.fiap.notepad.ui.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.ui.edit.EditViewModel
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_form.inputNote
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.fiap.notepad.constants.CallerConstants
import com.fiap.notepad.ui.list.ListActivity
import java.lang.Exception


class EditActivity : AppCompatActivity() {

    private val editViewModel: EditViewModel by viewModel()
    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if (intent.extras == null) {
            Toast.makeText(this, getString(R.string.activity_list_error_on_load),
                Toast.LENGTH_SHORT).show()
        } else {
            noteId = intent.extras?.getInt("note_id")!!
            inputNote.setText(intent.extras?.getString("note_text")!!)
        }

        btEdit.setOnClickListener {
            editNoteData()
        }

        btDelete.setOnClickListener {
            deleteNoteData()
        }
    }

    private fun editNoteData() {
        if (TextUtils.isEmpty(inputNote.text)) {
            Toast.makeText(this@EditActivity, R.string.activity_edit_required_text,
                Toast.LENGTH_SHORT).show()
        } else {
            val noteRequest: NoteRequest = NoteRequest(inputNote.text.toString())
            editViewModel.updateNote(noteId, noteRequest)
            try {
                Toast.makeText(this@EditActivity, R.string.activity_edit_update_success,
                    Toast.LENGTH_SHORT).show()
            } catch (ex: Exception){
                Toast.makeText(this@EditActivity, R.string.activity_edit_update_error,
                    Toast.LENGTH_SHORT).show()
            }
            val activity = Intent(this@EditActivity, ListActivity::class.java)
            activity.putExtra("caller", CallerConstants.ACTIVITY_EDIT_CALLER);
            startActivity(activity)
            finish()
        }
        clearData()
    }

    private fun deleteNoteData() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.activity_edit_delete_confirmation_message)
            .setCancelable(false)
            .setPositiveButton(R.string.activity_edit_delete_action_confim,
                DialogInterface.OnClickListener { dialog, id ->
                    try {
                        editViewModel.deleteNote(noteId)
                        Toast.makeText(this@EditActivity, R.string.activity_edit_delete_success,
                            Toast.LENGTH_SHORT).show()
                    } catch (ex: Exception){
                        Toast.makeText(this@EditActivity, R.string.activity_edit_delete_error,
                            Toast.LENGTH_SHORT).show()
                    }
                    val activity = Intent(this@EditActivity, ListActivity::class.java)
                    activity.putExtra("caller", CallerConstants.ACTIVITY_EDIT_CALLER);
                    startActivity(activity)
                    finish()
                })
            .setNegativeButton(R.string.activity_edit_delete_action_cancel,
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }

    private fun clearData() {
        inputNote.setText("")
    }
}
