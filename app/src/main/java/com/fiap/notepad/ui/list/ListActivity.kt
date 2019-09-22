package com.fiap.notepad.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.fiap.notepad.R
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    val listViewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

    listViewModel.getNotes()
    listViewModel.isLoading.observe(this, Observer {
        if(it == true) {
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
} }
