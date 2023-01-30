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

        val bt_nav = findViewById<BottomNavigationView>(R.id.bottomnav)

        bt_nav.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.tab1 -> {
                        //bt_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        //bt_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        MonthFragment()
                        // Respond to navigation item 1 click
                    }
                    R.id.tab2 -> {
                        //bt_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        //bt_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        DayFragment()
                        // Respond to navigation item 2 click
                    }
                    R.id.tab3 -> {
                        //bt_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        //bt_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        ClosingFragment()
                        // Respond to navigation item 3 click
                    }
                    else -> {
                        //bt_nav.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        //bt_nav.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        StatFragment()
                        // Respond to navigation item 3 click
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {{}
        when(item.itemId){
            R.id.item1->{
                val intent = Intent(this, PigBank::class.java)
                startActivity(intent)
            }
            R.id.item2->{
                val intent = Intent(this, AlbumActivity::class.java)
                startActivity(intent)
            }
            R.id.item3->{
                val intent = Intent(this, MainActivity::class.java)
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
