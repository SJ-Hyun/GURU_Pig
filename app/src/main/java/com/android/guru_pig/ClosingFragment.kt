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
    // TODO: Rename and change types of parameters
    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var layout2: LinearLayout
    lateinit var layout3: LinearLayout
    lateinit var layout4: LinearLayout

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
        layout3 = view.findViewById(R.id.plusText)
        layout4 = view.findViewById(R.id.minusText)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM plus", null)

        var num: Int = 0
        while(cursor.moveToNext()) {
            var money = cursor.getInt((cursor.getColumnIndex("money")))

            var layout_item: LinearLayout = LinearLayout(getActivity())
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvMoney: TextView = TextView(getActivity())
            tvMoney.text = money.toString()
            tvMoney.textSize = 18f
            layout_item.addView(tvMoney)

            layout2.addView(layout_item)
            num++
        }
        cursor.close()
        sqlitedb.close()
        dbManger.close()

        return view
    }


}