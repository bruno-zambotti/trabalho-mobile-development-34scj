package com.fiap.notepad.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteData
import com.fiap.notepad.model.NoteRequest
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    val listViewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (intent.extras == null) {
            Toast.makeText(this, getString(R.string.activity_list_error_on_load),
                Toast.LENGTH_SHORT).show()
        } else {
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

            val note = intent.extras?.getParcelable<NoteData>("new_note")!!.note
            listViewModel.createNote(NoteRequest(note))
            listViewModel.getNotes()
            listViewModel.isLoading.observe(this, Observer {
                if (it == true) {
                    containerLoading.visibility = View.VISIBLE
                } else {
                    containerLoading.visibility = View.GONE
                }
            })
            listViewModel.messageResponse.observe(this, Observer {
                if(it != "") {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}

