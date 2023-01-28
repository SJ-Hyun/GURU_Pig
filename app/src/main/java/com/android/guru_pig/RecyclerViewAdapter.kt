package com.android.guru_pig

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter internal constructor(var context: Context, var album: Array<Int>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var albumTitle = arrayOf(
        "돼지1", "돼지2", "돼지3", "돼지4", "돼지5","돼지6","돼지7","돼지8","돼지9","돼지10"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(album[position])
        //이미지 클릭시 dialog화면 보여주기
        holder.imageView.setOnClickListener {
            val dialogView = View.inflate(context, R.layout.album_dialog, null)
            val dlg = AlertDialog.Builder(
                context
            )
            val album_dial = dialogView.findViewById<ImageView>(R.id.albumImage2)
            album_dial.setImageResource(album[position])
            dlg.setIcon(R.drawable.pig)
            dlg.setTitle(albumTitle[position])
            dlg.setView(dialogView)
            dlg.setNegativeButton("닫기", null)
            dlg.show()
        }
    }

    //크기 가져오기
    override fun getItemCount(): Int {
        return album.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.albumImage1)
        }
    }
}