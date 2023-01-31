package com.android.guru_pig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.activity_pig_bank.*
import kotlinx.android.synthetic.main.album_dialog.*

class AlbumActivity : AppCompatActivity() {
    lateinit var albumImage1: ImageView
    lateinit var albumImage2: ImageView
    lateinit var albumImage3: ImageView
    lateinit var albumImage4: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        albumImage1 = findViewById(R.id.albumImage1)
        albumImage2 = findViewById(R.id.albumImage2)
        albumImage3 = findViewById(R.id.albumImage3)
        albumImage4= findViewById(R.id.albumImage4)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //이미지 클릭시 다이얼로그
       albumImage1.setOnClickListener{
            val dialogView = View.inflate(this, R.layout.album_dialog, null)
            var builder = AlertDialog.Builder(this)
            val album_dial = dialogView.findViewById<ImageView>(R.id.dial_album)
            album_dial.setImageResource(R.drawable.pigcha1)
            builder.setIcon(R.drawable.min_toolbar_pig_size)
            builder.setTitle("기본")
            builder.setNegativeButton("닫기", null)
            builder.show()
        }

        albumImage2.setOnClickListener{
            var builder = AlertDialog.Builder(this)

            val dialogView = View.inflate(this, R.layout.album_dialog, null)
            val album_dial = dialogView.findViewById<ImageView>(R.id.dial_album)
            album_dial.setImageResource(R.drawable.pigcha2)
            builder.setIcon(R.drawable.min_toolbar_pig_size)
            builder.setTitle("한살")
            builder.setNegativeButton("닫기", null)
            builder.show()
        }

        albumImage3.setOnClickListener{
            var builder = AlertDialog.Builder(this)

            val dialogView = View.inflate(this, R.layout.album_dialog, null)
            val album_dial = dialogView.findViewById<ImageView>(R.id.dial_album)
            album_dial.setImageResource(R.drawable.pigcha3)
            builder.setIcon(R.drawable.min_toolbar_pig_size)
            builder.setTitle("두살")
            builder.setNegativeButton("닫기", null)
            builder.show()
        }

        albumImage4.setOnClickListener{
            var builder = AlertDialog.Builder(this)

            val dialogView = View.inflate(this, R.layout.album_dialog, null)
            val album_dial = dialogView.findViewById<ImageView>(R.id.dial_album)
            album_dial.setImageResource(R.drawable.pigcha4)
            builder.setIcon(R.drawable.min_toolbar_pig_size)
            builder.setTitle("다섯살")
            builder.setNegativeButton("닫기", null)
            builder.show()
        }


        //저축액 달성시 앨범에 캐릭터 보이도록
        /*var tText = totalText.text.toString()
        var total: Int = tText.toInt()

        if(total<100000) albumImage1!!.setVisibility(View.VISIBLE)
        else if(total<200000) albumImage2!!.setVisibility(View.VISIBLE)
        else if(total<300000) albumImage3!!.setVisibility(View.VISIBLE)
        else albumImage4!!.setVisibility(View.VISIBLE)*/


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
