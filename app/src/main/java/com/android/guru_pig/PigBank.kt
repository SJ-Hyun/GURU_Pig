package com.android.guru_pig

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pig_bank.*
import java.io.ByteArrayOutputStream

class PigBank : AppCompatActivity() {
    lateinit var pigImage: ImageView

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pig_bank)

        //툴바
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //돼지이미지
        pigImage=findViewById(R.id.pigImage)


        //db
        dbManger = DBManger(this, "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase



        pushAlbum.setOnClickListener {
            val intent = Intent(this, AlbumActivity::class.java)
            startActivity(intent)
        }

        //저축액 달성시 캐릭터 전환
        /*var tText = totalText.text.toString()
        var total: Int = tText.toInt()

        if(total<100000) pigImage!!.setImageResource(R.drawable.pigcha1)
        else if(total<200000) pigImage!!.setImageResource(R.drawable.pigcha2)
        else if(total<300000) pigImage!!.setImageResource(R.drawable.pigcha3)
        else pigImage!!.setImageResource(R.drawable.pigcha4)*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}