package com.example.android.trackmysleepquality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.android.trackmysleepquality.database.BetoltesItem
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.recyclerview.widget.LinearLayoutManager


class ttsbetoltes : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: BetoltesAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var mExampleList= mutableListOf<BetoltesItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ttsbetoltes)

      // var exampleList:List<BetoltesItem>?=null
       // var exampleList:List<BetoltesItem> =
        val exampleList = mutableListOf<BetoltesItem>()

exampleList.add(BetoltesItem("1","111"))

        exampleList.add(BetoltesItem("4521","1124521"))
        exampleList.add(BetoltesItem("42541","178711"))
        exampleList.add(BetoltesItem("8731","1175831"))
        mRecyclerView = findViewById(R.id.recyclerview)
        mRecyclerView?.setHasFixedSize(true)

        mLayoutManager = LinearLayoutManager(this)
        mAdapter =  BetoltesAdapter(exampleList)

        mRecyclerView?.setLayoutManager(mLayoutManager)
        mRecyclerView?.setAdapter(mAdapter)
        /*
        mAdapter.setOnItemClickListener(new BetoltesAdapter.OnItemClickListener{
            override onItemclick(position:Int){

        }
        }
        )
*/
        fun buildRecyclerView (){
            mRecyclerView = findViewById(R.id.recyclerview);
            mRecyclerView?.setHasFixedSize(true);
            mLayoutManager = LinearLayoutManager(this);
            mAdapter = BetoltesAdapter(mExampleList);

            mRecyclerView?.setLayoutManager(mLayoutManager);
            mRecyclerView?.setAdapter(mAdapter);

            mAdapter?.setOnItemClickListener(BetoltesAdapter.OnItemClickListener {

                public void onItemClick(int position) {
                    changeItem(position, "Clicked");
                }
            });
/*


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        */


/*
listView = findViewById(R.id.listView) as ListView
textView = findViewById(R.id.textView) as TextView
listItem = resources.getStringArray(R.array.array_technology)
val adapter = ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, android.R.id.text1, listItem)
listView.setAdapter(adapter)

listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
    fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
        // TODO Auto-generated method stub
        val value = adapter.getItem(position)
        Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT).show()

    }
})
*/
    }
}
