package com.fiap.notepad.ui.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteData
import com.fiap.notepad.ui.form.FormActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<NoteData>();
    private var mCheckedPosition = -1;

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.tvRecyclerItem)
        val radioItemView: RadioButton = itemView.findViewById(R.id.rRecyclerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteItemView.text = current.note
        holder.radioItemView.id = current.id
        holder.apply {
            radioItemView.isChecked = position == mCheckedPosition
            radioItemView.setOnClickListener {
                if(position == mCheckedPosition) {
                    radioItemView.isChecked = false
                    mCheckedPosition = -1
                } else {
                    mCheckedPosition = position
                    notifyDataSetChanged()
                }
            }
        }
    }

    internal fun setNotes(notes: List<NoteData>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}