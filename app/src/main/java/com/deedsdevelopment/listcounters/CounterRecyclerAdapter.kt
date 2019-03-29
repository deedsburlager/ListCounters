package com.deedsdevelopment.listcounters
/*
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CounterRecyclerAdapter(countersList: List<Counters>, internal var context: Context) : RecyclerView.Adapter<CounterRecyclerAdapter.CounterViewHolder>() {
        internal var countersList: List<Counters> = ArrayList()
        init {
        this.countersList = countersList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_counters, parent, false)
        return CounterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        val counters = countersList[position]
        holder.name.text = counters.name
        holder.tally.text = counters.tally
        if (counters.completed == "Y") {
            holder.list_item_counters.background = ContextCompat.getDrawable(context, R.color.colorSuccess)
        }else {
            holder.list_item_counters.background = ContextCompat.getDrawable(context, R.color.colorUnSuccess)
        }
        holder.itemView.setOnClickListener {
            val i = Intent(context, CreateOrUpdateActivity::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", counters.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_COUNTER
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return countersList.size
    }

    inner class counterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.counterName) as TextView
        var tally: TextView = view.findViewById(R.id.counterTally) as TextView
        var list_item: LinearLayout = view.findViewById(R.id.list_item) as LinearLayout
    }
    } */

/*
package com.example.paulallies.myrecyclerviewapp

data class User(val name: String, val title: String)

 */
//import android.support.v7.widget.RecyclerView
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.util.ArrayList
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView

import com.deedsdevelopment.listcounters.Counters
import com.deedsdevelopment.listcounters.CreateUpdateActivity

class CounterRecyclerAdapter(countersList: List<Counters>, internal var context: Context): RecyclerView.Adapter<CounterRecyclerAdapter.ViewHolder>() {

    internal var countersList: List<Counters> = ArrayList()
    init {
        this.countersList = countersList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val counters = countersList[position]
        holder.textView?.text = countersList[position].name
        holder.textView2?.text = countersList[position].tally
        if (counters.completed == "Y")
            holder.list_item.background = getDrawable(context, R.color.colorSuccess)
        else
            holder.list_item.background = getDrawable(context, R.color.colorUnSuccess)

        holder.itemView.setOnClickListener {
            val i = Intent(context, CreateUpdateActivity ::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", counters.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_counters, parent, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return countersList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val textView2 = itemView.findViewById<TextView>(R.id.textView2)
        var list_item: LinearLayout = itemView.findViewById(R.id.list_item) as LinearLayout
//completed section needs att
    }

}