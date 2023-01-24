package com.android.guru_pig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fl: FrameLayout by lazy {
        findViewById(R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}