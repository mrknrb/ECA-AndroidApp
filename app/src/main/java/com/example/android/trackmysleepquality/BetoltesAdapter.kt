package com.example.android.trackmysleepquality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.BetoltesAdapter.ExampleViewHolder

import com.example.android.trackmysleepquality.database.BetoltesItem

import java.util.ArrayList


class BetoltesAdapter(private val mExampleList: MutableList<BetoltesItem>) : RecyclerView.Adapter<BetoltesAdapter.ExampleViewHolder>() {

  var mListener:OnItemClickListener?=null

 interface OnItemClickListener{
    fun onItemClick(position:Int)

}
fun setOnItemClickListener(listener:OnItemClickListener){
    mListener=listener
}

    class ExampleViewHolder(itemView: View,listener:OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView
        var mTextView1: TextView
        var mTextView2: TextView

        init {
            mImageView = itemView.findViewById(R.id.imageView)
            mTextView1 = itemView.findViewById(R.id.textView)
            mTextView2 = itemView.findViewById(R.id.textView2)

            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.betoltesitem, parent, false)


        return ExampleViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = mExampleList[position]

        holder.mTextView1.text = currentItem.getText1()
        holder.mTextView2.text = currentItem.getText2()
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}