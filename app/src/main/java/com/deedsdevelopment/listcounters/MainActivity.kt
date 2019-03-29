package com.deedsdevelopment.listcounters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deedsdevelopment.listcounters.DBHandler
import com.deedsdevelopment.listcounters.Counters
import com.deedsdevelopment.listcounters.CounterRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var counterRecyclerAdapter: CounterRecyclerAdapter? = null;
    var fab: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var dbHandler: DBHandler? = null
    var listCounters: List<Counters> = ArrayList<Counters>()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initOperations()
        //initDB()
        setSupportActionBar(toolbar)

        /*val rv = findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val counters = ArrayList<Counters>()

        val adapter = CounterRecyclerAdapter(counters)
        rv.adapter = adapter

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findViewById(R.id.fab).setOnClickListener {
                startActivity(Intent(applicationContext, RecyclerViewActivity::class.java))
            }
        }
        */
         }
    fun initDB() {
        dbHandler = DBHandler(this)
        listCounters = (dbHandler as DBHandler).counter()
        counterRecyclerAdapter = CounterRecyclerAdapter(countersList = listCounters, context = applicationContext)
        (recyclerView as RecyclerView).adapter = counterRecyclerAdapter
    }

    fun initViews() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        fab = findViewById(R.id.fab) as FloatingActionButton
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        counterRecyclerAdapter = CounterRecyclerAdapter(countersList = listCounters, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
    }

    fun initOperations() {
        fab?.setOnClickListener { view ->
            val i = Intent(applicationContext, CreateUpdateActivity::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Yes will delete all counters")
                .setPositiveButton("YES", { dialog, i ->
                    dbHandler!!.deleteAllTasks()
                    initDB()
                    dialog.dismiss()
                })
                .setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
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