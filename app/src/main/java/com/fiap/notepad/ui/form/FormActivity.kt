package com.fiap.notepad.ui.form

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.fiap.notepad.utils.DatabaseUtil
import com.fiap.notepad.R
import com.fiap.notepad.constants.CallerConstants
import com.fiap.notepad.model.NoteData
import com.fiap.notepad.ui.about.AboutActivity
import com.fiap.notepad.ui.list.ListActivity
import com.fiap.notepad.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var mAuth: FirebaseAuth
    private val firebaseReferenceNode = "NoteData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        mAuth = FirebaseAuth.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        listenerFirebaseRealtime()

        btSave.setOnClickListener {
            saveNoteData()
        }

        btAllNotes.setOnClickListener{
            allNotes()
        }
    }

    private fun saveNoteData() {
        if (TextUtils.isEmpty(inputNote.text)) {
            Toast.makeText(this@FormActivity, R.string.activity_form_required_text,
                Toast.LENGTH_SHORT).show()
        } else {
            val noteData = NoteData()
            noteData.note = inputNote.text.toString()
            val activity = Intent(this@FormActivity, ListActivity::class.java)
            activity.putExtra("new_note", noteData)
            activity.putExtra("caller", CallerConstants.ACTIVITY_FORM_CALLER);
            startActivity(activity)
        }
        clearData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_clear -> {
                clearData()
                return true
            }
            R.id.action_logout -> {
                logout()
                return true
            }
            R.id.action_allNotes -> {
                allNotes()
                return true
            }
            R.id.action_about -> {
                about()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        mAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun allNotes() {
        val activity = Intent(this@FormActivity, ListActivity::class.java)
        activity.putExtra("caller", CallerConstants.MENU_FORM_CALLER);
        startActivity(activity)
    }

    private fun about() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun clearData() {
        inputNote.setText("")
    }

    private fun listenerFirebaseRealtime() {
        val database = DatabaseUtil.getDatabase()
        //Define para usar dados off-line
        //database.setPersistenceEnabled(true)
        database
            .getReference(firebaseReferenceNode)
            .child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}
