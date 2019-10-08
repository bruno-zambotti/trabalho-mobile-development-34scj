package com.fiap.notepad.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.notepad.R
import com.fiap.notepad.constants.CallerConstants
import com.fiap.notepad.model.NoteData
import com.fiap.notepad.model.NoteRequest
import com.fiap.notepad.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    val listViewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        fab.setOnClickListener {
            val activity = Intent(this, FormActivity::class.java)
            startActivity(activity)
        }

        if (intent.extras == null) {
            Toast.makeText(this, getString(R.string.activity_list_error_on_load),
                Toast.LENGTH_SHORT).show()
        } else {
            val caller = intent.getIntExtra("caller", 0)

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            val adapter = ListAdapter(this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            listViewModel.notes.observe(this, Observer { notes ->
                var noteDataList: List<NoteData> = notes.map { NoteData(it.id, it.note) };
                notes?.let {
                    adapter.setNotes(noteDataList)
                }
            })

            if (caller == CallerConstants.ACTIVITY_FORM_CALLER) {
                val note = intent.extras?.getParcelable<NoteData>("new_note")!!.note
                listViewModel.createNote(NoteRequest(note))
                listViewModel.isNoteCreated.observe(this, Observer {
                    if (it == true) {
                        handleNotes()
                        it == false
                    }
                })
            } else if (caller == CallerConstants.MENU_FORM_CALLER ||
                       caller == CallerConstants.ACTIVITY_EDIT_CALLER){
                handleNotes()
            } else {
                Toast.makeText(this, getString(R.string.activity_list_error_on_load),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleNotes() {
        listViewModel.getNotes()
        listViewModel.isLoading.observe(this, Observer {
            if (it == true) {
                containerLoading.visibility = View.VISIBLE
            } else {
                containerLoading.visibility = View.GONE
            }
        })
    }
}

