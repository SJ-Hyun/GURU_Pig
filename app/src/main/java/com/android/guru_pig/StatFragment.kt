package com.android.guru_pig

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.os.DropBoxManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import org.w3c.dom.Entity
import java.security.KeyStore
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [stat.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatFragment : Fragment() {

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var chart1: PieChart
    lateinit var chart2: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_stat, container, false)

        dbManger = DBManger(getActivity(), "accountDB", null, 1)
        sqlitedb = dbManger.readableDatabase
        chart1 = view.findViewById(R.id.chart1)
        chart2 = view.findViewById(R.id.chart2)

        //월 지출 구하기
        var month : Int = Calendar.getInstance().get(Calendar.MONTH)+1
        var monthMinus = 0
        var monthPlus = 0

        var minusArray = arrayOf("식비","교통","문화생활","편의생활","패션/미용","주거/통신","건강","교육","식비","경조사/선물","반려동물","기타")
        var plusArray = arrayOf("월급","부수입","용돈","상여금","금융소득","기타")

        var minusMoneyArray = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0)
        var plusMoneyArray = arrayOf(0,0,0,0,0,0)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT class, money FROM minus WHERE month="+month, null)
        while(cursor.moveToNext()) {
            var str_class = cursor.getString(cursor.getColumnIndex("class")).toString()
            var money = cursor.getInt(cursor.getColumnIndex("money"))
            monthMinus += money
            minusMoneyArray[minusArray.indexOf(str_class)] += money
        }
        cursor = sqlitedb.rawQuery("SELECT class, money FROM plus WHERE month="+month, null)
        while(cursor.moveToNext()) {
            var str_class = cursor.getString(cursor.getColumnIndex("class")).toString()
            var money = cursor.getInt(cursor.getColumnIndex("money"))
            monthPlus += money
            plusMoneyArray[plusArray.indexOf(str_class)] += money
        }

        cursor.close()
        sqlitedb.close()
        dbManger.close()

        val minus_entries = ArrayList<PieEntry>()
        val plus_entries = ArrayList<PieEntry>()
        chart1.setUsePercentValues(true)
        chart2.setUsePercentValues(true)

        for(i in 0..minusArray.size-1){
            if(minusMoneyArray[i] != 0)
                minus_entries.add(PieEntry(minusMoneyArray[i].toFloat(), minusArray[i]))
        }

        for(i in 0..plusArray.size-1){
            if(plusMoneyArray[i] != 0)
                plus_entries.add(PieEntry(plusMoneyArray[i].toFloat(), plusArray[i]))
        }

        // 색깔
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet1 = PieDataSet(minus_entries, "")
        pieDataSet1.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        val pieDataSet2 = PieDataSet(plus_entries, "")
        pieDataSet2.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        val pieData1 = PieData(pieDataSet1)
        chart1.apply {
            data = pieData1
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "월간 지출 내역\n"+monthMinus+"원"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
        val pieData2 = PieData(pieDataSet2)
        chart2.apply {
            data = pieData2
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "월간 수입 내역\n"+monthPlus+"원"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
        return view
    }
}