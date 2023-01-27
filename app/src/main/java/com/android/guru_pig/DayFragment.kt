package com.android.guru_pig

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.nio.file.attribute.AclEntry
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [DayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayFragment : Fragment() {

    lateinit var addBtn : Button

    lateinit var layout: LinearLayout

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    //lateinit var dayDate : DayScrollDatePicker
    lateinit var calTextView: TextView
    lateinit var startDate:Calendar
    lateinit var endDate:Calendar
    lateinit var horizontalCalendar: HorizontalCalendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_day, container, false)
        addBtn = view.findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
            activity?.let{
                val intent = Intent(context, DayInput::class.java)
                startActivity(intent)
            }
        }

        dbManger = DBManger(getActivity(), "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase

        layout = view.findViewById(R.id.list)

        //주간달력
        calTextView=view.findViewById(R.id.calTextView)

        //시작날짜
        startDate=Calendar.getInstance()
        startDate.add(Calendar.MONTH,-1)

        //종료날짜
        endDate=Calendar.getInstance()
        endDate.add(Calendar.MONTH,1)

        //가로달력 실행
        horizontalCalendar= HorizontalCalendar.Builder(view,R.id.HCalendar)
            .range(startDate,endDate)
            .datesNumberOnScreen(7)
            .build()

        //날짜 선택 이벤트
        horizontalCalendar.setCalendarListener(object : HorizontalCalendarListener(){
            override fun onDateSelected(date:Calendar, position:Int){
                println("안녕하세요")
            }
        })
        //dayDate=view.findViewById(R.id.dayDate)
        //dayDate.setStartDate(1,1,2023)
        //dayDate.getSelectedDate(OnDateSelectedListener(){ })


        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM plus", null)

        var num: Int = 0
        while(cursor.moveToNext()) {
            var str_class = cursor.getString(cursor.getColumnIndex("class")).toString()
            var money = cursor.getInt((cursor.getColumnIndex("money")))
            var str_content = cursor.getInt((cursor.getColumnIndex("content"))).toString()

            var layout_item: LinearLayout = LinearLayout(getActivity())
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvClass: TextView = TextView(getActivity())
            tvClass.text = str_class
            tvClass.textSize = 30f
            tvClass.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvClass)

            var tvMoney: TextView = TextView(getActivity())
            tvMoney.text = money.toString()
            layout_item.addView(tvMoney)

            var tvContent: TextView = TextView(getActivity())
            tvContent.text = str_content
            layout_item.addView(tvContent)

            num++
        }
        cursor.close()
        sqlitedb.close()
        dbManger.close()


        // Inflate the layout for this fragment
        return view
    }
}