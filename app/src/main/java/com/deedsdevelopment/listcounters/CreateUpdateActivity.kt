package com.deedsdevelopment.listcounters

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_update.*
import com.deedsdevelopment.listcounters.Counters
import com.deedsdevelopment.listcounters.DBHandler


class CreateUpdateActivity : AppCompatActivity() {

    var dbHandler: DBHandler? = null
    var isEditMode = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DBHandler(this)
        btn_delete.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val counters: Counters = dbHandler!!.getCounter(intent.getIntExtra("Id",0))
            input_counter_name.setText(counters.name)
            input_counter_tally.setText(counters.tally)
            swt_completed.isChecked = counters.completed == "Y"
            btn_delete.visibility = View.VISIBLE
        }
    }

    private fun initOperations() {
        btn_save.setOnClickListener({
            var success: Boolean = false
            if (!isEditMode) {
                val counters: Counters = Counters()
                counters.name = input_counter_name.text.toString()
                counters.tally = input_counter_tally.text.toString()
                if (swt_completed.isChecked)
                    counters.completed = "Y"
                else
                    counters.completed = "N"
                success = dbHandler?.addCounter(counters) as Boolean
            } else {
                val counters: Counters = Counters()
                counters.id = intent.getIntExtra("Id", 0)
                counters.name = input_counter_name.text.toString()
                counters.tally = input_counter_tally.text.toString()
                if (swt_completed.isChecked)
                    counters.completed = "Y"
                else
                    counters.completed = "N"
                success = dbHandler?.updateCounter(counters) as Boolean
            }

            if (success)
                finish()
        })

        btn_delete.setOnClickListener({
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Yes will delete counter.")
                .setPositiveButton("YES", { dialog, i ->
                    val success = dbHandler?.deleteCounter(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                })
                .setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}