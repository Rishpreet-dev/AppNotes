package com.example.appnotes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class NotesEditorActivity : AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_editor)
        val editText = findViewById<EditText>(R.id.editText)
        val intent = intent
        id = intent.getIntExtra("noteID", -1)
        if (id != -1) {
            editText.setText(MainActivity.notes[id])
        } else {
            MainActivity.notes.add("")
            id = MainActivity.notes.size - 1
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                MainActivity.notes[id] = s.toString()
                MainActivity.arrayAdapter?.notifyDataSetChanged()
                val sharedPreferences = applicationContext.getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE)
                val set = HashSet(MainActivity.notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}