package com.deedsdevelopment.listcounters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deedsdevelopment.listcounters.R.id.action_delete
import com.deedsdevelopment.listcounters.adapter.CounterRecyclerAdapter
import com.deedsdevelopment.listcounters.database.DBHandler
import com.deedsdevelopment.listcounters.model.Counters
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var counterRecyclerAdapter: CounterRecyclerAdapter? = null
    var fab: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var dbHandler: DBHandler? = null
    var listCounters: List<Counters> = ArrayList()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initOperations()
        initDB()
        setSupportActionBar(toolbar)
         }

    fun initDB() {
        dbHandler = DBHandler(this)
        listCounters = (dbHandler as DBHandler).counter()
        counterRecyclerAdapter = CounterRecyclerAdapter(
            countersList = listCounters,
            context = applicationContext
        )
        (recyclerView as RecyclerView).adapter = counterRecyclerAdapter
    }

    fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recycler_view)
        counterRecyclerAdapter = CounterRecyclerAdapter(
            countersList = listCounters,
            context = applicationContext
        )
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
    }

    fun initOperations() {
        fab?.setOnClickListener {
            val i = Intent(applicationContext, CreateUpdateActivity::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == action_delete) {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Delete all counters?")
                .setPositiveButton("YES") { dialog, i ->
                    dbHandler!!.deleteAllCounters()
                    initDB()
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, i ->
                    dialog.dismiss()
                }
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}