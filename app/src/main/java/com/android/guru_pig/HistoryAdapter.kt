package com.android.guru_pig

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val itemList: ArrayList<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryAdapter>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return HistoryAdapter(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter, position: Int) {
        //holder.rg_acc.checkedRadioButtonId = itemList[position].acc
        holder.tv_class.text = itemList[position].aclass
        holder.tv_content.text = itemList[position].content
        holder.tv_money.text = itemList[position].money
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class HistoryAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_class = itemView.findViewById<TextView>(R.id.tvClass)
        val tv_content = itemView.findViewById<TextView>(R.id.tvContent)
        val tv_money = itemView.findViewById<TextView>(R.id.tvMoney)

        init{
            itemView.setOnClickListener{
                itemClickListener?.onItemClick(adapterPosition)

            }
        }
    }
}

data class HistoryItem(val acc: String, val aclass: String, val content: String, val money: String)

