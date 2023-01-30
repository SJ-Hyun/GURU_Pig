package com.android.guru_pig

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ClosingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClosingFragment : Fragment() {

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var layout2: LinearLayout

    lateinit var plusTextView: TextView
    lateinit var minusTextView: TextView

    lateinit var goalBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_closing, container, false)

        dbManger = DBManger(getActivity(), "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase

        layout2 = view.findViewById(R.id.closeText)
        plusTextView = view.findViewById(R.id.plusText)
        minusTextView = view.findViewById(R.id.minusText)

        goalBtn = view.findViewById(R.id.goalBtn)

        //goalBtn 클릭하면 목표 지출액이 db에 입력돼야 함
        goalBtn.setOnClickListener {

        }

        //월별
        var cursor: Cursor
        var month: Int = 12

        for (i in 1..month){
            var monthPlus = 0
            var monthMinus = 0

            var layout_item: LinearLayout = LinearLayout(getActivity())
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = i

            cursor = sqlitedb.rawQuery("SELECT money FROM plus WHERE month="+i, null)

            while(cursor.moveToNext()) {
                var money = cursor.getInt(0)
                monthPlus += money
            }

            cursor = sqlitedb.rawQuery("SELECT money FROM minus WHERE month="+i, null)
            while(cursor.moveToNext()) {
                var money = cursor.getInt(0)
                monthMinus += money
            }

            var tvMonth: TextView = TextView(getActivity())
            tvMonth.text = i.toString()+"월"
            tvMonth.textSize = 20f
            tvMonth.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvMonth)

            var tvMonthPlus: TextView = TextView(getActivity())
            tvMonthPlus.text = "총수입 : " + monthPlus.toString()
            layout_item.addView(tvMonthPlus)

            var tvMonthMinus: TextView = TextView(getActivity())
            tvMonthMinus.text = "총지출 : " + monthMinus.toString()
            layout_item.addView(tvMonthMinus)

            layout2.addView(layout_item)

        }

        //총수입
        cursor = sqlitedb.rawQuery("SELECT money FROM plus", null)

        var allPlus: Int = 0
        while(cursor.moveToNext()) {
            var money = cursor.getInt(0)
            allPlus += money
        }

        plusTextView.text = allPlus.toString()

        //총지출
        cursor = sqlitedb.rawQuery("SELECT money FROM minus", null)
        var allMinus: Int = 0
        while(cursor.moveToNext()) {
            var money = cursor.getInt(0)
            allMinus += money
        }
        minusTextView.text = allMinus.toString()

        cursor.close()
        sqlitedb.close()
        dbManger.close()

        return view
    }


}