package com.android.guru_pig

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_day.*
import kotlinx.android.synthetic.main.fragment_day.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [DayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayFragment : Fragment() {

    lateinit var btnAdd : Button
    lateinit var layout: LinearLayout

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var rv_histoy: RecyclerView

    lateinit var btnPlus : Button
    lateinit var btnMinus : Button

    lateinit var startDate: Calendar
    lateinit var endDate: Calendar

    private lateinit var horizontalCalendar: HorizontalCalendar

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_day, container, false)

        dbManger = DBManger(getActivity(), "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase

        rv_histoy = rootView.findViewById(R.id.history_rv)

        btnPlus = rootView.findViewById(R.id.btnPlus)
        btnMinus = rootView.findViewById(R.id.btnMinus)
        btnAdd = rootView.findViewById(R.id.btnAdd)

        var selected: String = "plus"

        //*주간달력*
        //시작날짜
        startDate=Calendar.getInstance()
        startDate.add(Calendar.MONTH,-1)

        //종료날짜
        endDate=Calendar.getInstance()
        endDate.add(Calendar.MONTH,1)

        //가로달력 실행
        horizontalCalendar= HorizontalCalendar.Builder(rootView,R.id.HCalendar)
            .range(startDate,endDate)
            .datesNumberOnScreen(7)
            .build()

        var selectedDay : Calendar = Calendar.getInstance()
        year = selectedDay.get(Calendar.YEAR)
        month = selectedDay.get(Calendar.MONTH)+1
        day = selectedDay.get(Calendar.DAY_OF_MONTH)

        selectChange(selected)

        //날짜선택 이벤트
        horizontalCalendar.setCalendarListener(object : HorizontalCalendarListener(){
            @SuppressLint("Range")
            override fun onDateSelected(date:Calendar, position:Int){
                year = date.get(Calendar.YEAR)
                month = date.get(Calendar.MONTH)+1
                day = date.get(Calendar.DAY_OF_MONTH)
                selectChange(selected)
            }
        })


        btnPlus.setOnClickListener {
            selected = "plus"
            selectChange(selected)
        }

        btnMinus.setOnClickListener {
            selected = "minus"
            selectChange(selected)
        }

        btnAdd.setOnClickListener {
            val dialog = DayDialog(requireActivity())
            dialog.showDialog(year, month, day, selected)
        }

        return rootView
    }

    @SuppressLint("Range")
    fun showHistory(acc: String, year: Int, month: Int, day: Int) {
        sqlitedb = dbManger.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery(
            "SELECT * FROM " + acc + " WHERE year=" + year + " AND month=" + month + " AND day=" + day,
            null
        )

        val itemList = ArrayList<HistoryItem>()
        while (cursor.moveToNext()) {
            var str_class = cursor.getString(cursor.getColumnIndex("class")).toString()
            var money = cursor.getInt(cursor.getColumnIndex("money"))
            var str_content = cursor.getString(cursor.getColumnIndex("content")).toString()

            itemList.add(HistoryItem(acc, str_class, str_content, money.toString()))
        }
        cursor.close()
        sqlitedb.close()
        dbManger.close()

        val historyAdapter = HistoryAdapter(itemList)
        historyAdapter.notifyDataSetChanged()

        rv_histoy.adapter = historyAdapter
        rv_histoy.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)

        historyAdapter.itemClickListener = object : HistoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = itemList[position]

                val dialog = DayDialog(requireActivity())
                dialog.updateDialog(year, month, day, item.acc, item.aclass, item.content, item.money)
            }
        }
    }

    fun selectChange(selected: String){
        showHistory(selected, year, month, day)
        if (selected == "plus"){
            btnPlus.setBackgroundColor(Color.rgb(225,133,165))
            btnPlus.setTextColor(Color.WHITE)
            btnMinus.setBackgroundColor(Color.WHITE)
            btnMinus.setTextColor(Color.rgb(225,133,165))
        }else{
            btnMinus.setBackgroundColor(Color.rgb(225,133,165))
            btnMinus.setTextColor(Color.WHITE)
            btnPlus.setBackgroundColor(Color.WHITE)
            btnPlus.setTextColor(Color.rgb(225,133,165))
        }

    }
}