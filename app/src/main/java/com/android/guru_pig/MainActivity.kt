package com.android.guru_pig


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val fl: FrameLayout by lazy {
        findViewById(R.id.container)
    }

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
                val intent = Intent(this, MainActivity::class.java)
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