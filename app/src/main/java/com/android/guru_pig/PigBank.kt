package com.android.guru_pig

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_pig_bank.*

class PigBank : AppCompatActivity() {
    lateinit var pigImage: ImageView

    lateinit var totalText: TextView

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

        //총 저축액
        totalText=findViewById(R.id.totalText)

        //db
        dbManger = DBManger(this, "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase

        var cursor: Cursor

        var totalSaving = 0

        for (i in 1..12) {
            var g_money = 0
            var monthMinus = 0

            cursor = sqlitedb.rawQuery("SELECT g_money FROM goal WHERE month=" + i, null)
            while (cursor.moveToNext()) {
                var money = cursor.getInt(0)
                g_money = money
            }

            cursor = sqlitedb.rawQuery("SELECT money FROM minus WHERE month=" + i, null)
            while (cursor.moveToNext()) {
                var money = cursor.getInt(0)
                monthMinus += money
            }

            totalSaving += g_money-monthMinus

            cursor.close()
        }

        sqlitedb.close()
        dbManger.close()

        //저축액 달성시 캐릭터 전환
        totalText.text = totalSaving.toString()

        if(totalSaving<100000) pigImage!!.setImageResource(R.drawable.pigcha1)
        else if(totalSaving<200000) pigImage!!.setImageResource(R.drawable.pigcha2)
        else if(totalSaving<300000) pigImage!!.setImageResource(R.drawable.pigcha3)
        else pigImage!!.setImageResource(R.drawable.pigcha4)


        //앨범 버튼 선택
        pushAlbum.setOnClickListener {
            val intent = Intent(this, AlbumActivity::class.java)
            intent.putExtra("total",totalSaving)
            startActivity(intent)
        }


    }

    //뒤로 가기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}