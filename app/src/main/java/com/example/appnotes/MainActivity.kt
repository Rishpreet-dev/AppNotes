package com.example.appnotes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    fun DialogBox(itemtodel: Int) {
        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("DELETE ITEM SELECTED?")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("YES") { dialog, which ->
                    notes.removeAt(itemtodel)
                    arrayAdapter!!.notifyDataSetChanged()
                    val set = HashSet(notes)
                    sharedPreferences!!.edit().putStringSet("notes", set).apply()
                }
                .setNegativeButton("NO", null)
                .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return if (item.itemId == R.id.add_note) {
            val intent2 = Intent(applicationContext, NotesEditorActivity::class.java)
            startActivity(intent2)
            true
        } else {
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mylistView = findViewById<ListView>(R.id.listView)
        sharedPreferences = applicationContext.getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE)

        val set = sharedPreferences.getStringSet("notes", null) as HashSet<String>?
        if (set != null) {
            notes = ArrayList(set)
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        mylistView.adapter = arrayAdapter
        mylistView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val intent = Intent(applicationContext, NotesEditorActivity::class.java)
            intent.putExtra("noteID", position)
            startActivity(intent)
        }
        mylistView.onItemLongClickListener = OnItemLongClickListener { parent, view, position, id ->
            DialogBox(position)
            true
        }
    }

    companion object {
        var notes = ArrayList<String>()
        var arrayAdapter: ArrayAdapter<String>? = null
    }
}