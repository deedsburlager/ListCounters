// package com.deedsdevelopment.listcounters
//
// import android.os.Bundle
// import androidx.appcompat.app.AppCompatActivity
// import androidx.recyclerview.widget.DefaultItemAnimator
// import androidx.recyclerview.widget.LinearLayoutManager
// import androidx.recyclerview.widget.RecyclerView
//
// class RecyclerViewActivity : AppCompatActivity() {
// private var counterList = ArrayList<Counters>()
// private var recyclerView: RecyclerView? = null
// private var adapter: CounterRecyclerAdapter? = null
//
// override fun onCreate(savedInstanceState: Bundle?) {
// super.onCreate(savedInstanceState)
// setContentView(R.layout.activity_recyclerview)
//
// recyclerView = findViewById(R.id.content_main) as RecyclerView
//
// adapter = CounterRecyclerAdapter(counterList)
// val mLayoutManager = LinearLayoutManager(applicationContext)
// recyclerView!!.layoutManager = layoutManager
// recyclerView!!.itemAnimator = DefaultItemAnimator()
// recyclerView!!.adapter = adapter
//
// prepareCounterData()
// }