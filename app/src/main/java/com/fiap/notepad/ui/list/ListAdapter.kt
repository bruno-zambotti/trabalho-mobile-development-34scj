package com.fiap.notepad.ui.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.notepad.R
import com.fiap.notepad.model.NoteData
import com.fiap.notepad.ui.edit.EditActivity

class ListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<NoteData>();
    private var context = context;

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.tvRecyclerItem)
        val imageItemView: ImageView = itemView.findViewById(R.id.ivRecyclerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteItemView.text = current.note

        holder.imageItemView.setOnClickListener{
            val activity = Intent(context, EditActivity::class.java)
            activity.putExtra("note_id", current.id)
            activity.putExtra("note_text", current.note)
            context.startActivity(activity)
        }
    }

    internal fun setNotes(notes: List<NoteData>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}