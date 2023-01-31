package com.android.guru_pig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        //툴바
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "앨범"

        //리사이클러뷰
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        val adapter = RecyclerViewAdapter(this, album)
        recyclerView.adapter = adapter
    }


    //뒤로 가기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //돼지 사진
    var album = arrayOf<Int>(
        R.drawable.pigcha1, R.drawable.pigcha2, R.drawable.pigcha3,R.drawable.pigcha4
    )


}