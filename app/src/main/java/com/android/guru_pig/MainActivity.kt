package com.android.guru_pig

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

open class MyApp: Application(){
    lateinit var context: Context
    init {instance=this}
    companion object{
        private var instance: MyApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        //바텀 메뉴
        val bt_nav = findViewById<BottomNavigationView>(R.id.bottomnav)

        bt_nav.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.tab1 -> { //월별 프래그먼트
                        MonthFragment()
                    }
                    R.id.tab2 -> { //일별 프래그먼트
                        DayFragment()
                    }
                    R.id.tab3 -> { //통계 프래그먼트
                        StatFragment()
                    }
                    else -> { //결산 프래그먼트
                        ClosingFragment()
                    }
                }
            )
            true
        }
        bt_nav.selectedItemId = R.id.tab1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1->{
                val intent = Intent(this, PigBank::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}
