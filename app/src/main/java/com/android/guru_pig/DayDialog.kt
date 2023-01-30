package com.android.guru_pig

import android.app.Dialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.guru_pig.MyApp.Companion.applicationContext
import java.util.ArrayList

class DayDialog(context: Context) {
    private val dialog = Dialog(context)

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    fun showDialog(year: Int, month: Int, day: Int){
        dialog.setContentView(R.layout.activity_day_input)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val rg_account = dialog.findViewById<RadioGroup>(R.id.rg_acc)
        val rb_plus = dialog.findViewById<RadioButton>(R.id.plus)
        val rb_minus = dialog.findViewById<RadioButton>(R.id.minus)
        val spinClass = dialog.findViewById<Spinner>(R.id.spinClass)
        val edtMoney = dialog.findViewById<EditText>(R.id.edtMoney)
        val edtContent = dialog.findViewById<EditText>(R.id.edtContent)
        val inputBtn = dialog.findViewById<Button>(R.id.btnInput)
        val cancelBtn = dialog.findViewById<Button>(R.id.btnCancel)


        val acontext: Context = applicationContext()
        dbManger = DBManger(acontext, "accountDB", null, 1)


        if (rb_plus.isChecked == true) {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.plusList, android.R.layout.simple_spinner_dropdown_item)
        }else {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.minusList, android.R.layout.simple_spinner_dropdown_item)
        }

        var str_Class: String = ""
        spinClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("항목을 선택하세요")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                str_Class = spinClass.selectedItem.toString()

            }
        }

        inputBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase

            var str_account: String = ""
            if (rg_account.checkedRadioButtonId == R.id.plus) {
                str_account = "plus"
            }
            if (rg_account.checkedRadioButtonId == R.id.minus) {
                str_account = "minus"
            }

            var str_Money: String = edtMoney.text.toString()
            var str_Content: String = edtContent.text.toString()

            sqlitedb.execSQL("INSERT INTO "+str_account+" VALUES('"+year+"','"+month+"','"+day+"','"+str_Class+"','"+str_Money+"','"+str_Content+"')")
            sqlitedb.close()
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun updateDialog(year: Int, month: Int, day: Int, acc:String, aclass: String, content: String, money: String){
        dialog.setContentView(R.layout.day_update)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val spinClass = dialog.findViewById<Spinner>(R.id.spinClass)
        val edtMoney = dialog.findViewById<EditText>(R.id.edtMoney)
        val edtContent = dialog.findViewById<EditText>(R.id.edtContent)

        val deleteBtn = dialog.findViewById<Button>(R.id.btnDelete)
        val updateBtn = dialog.findViewById<Button>(R.id.btnUpdate)

        val acontext: Context = applicationContext()

        if (acc == "plus") {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.plusList, android.R.layout.simple_spinner_dropdown_item)
        }
        if (acc == "minus") {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.minusList, android.R.layout.simple_spinner_dropdown_item)
        }

        var str_Class: String = ""
        spinClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("항목을 선택하세요")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                str_Class = spinClass.selectedItem.toString()

            }
        }

        updateBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase

            var str_Money: String = edtMoney.text.toString()
            var str_Content: String = edtContent.text.toString()

            sqlitedb.execSQL(
                "UPDATE " + acc
                        + " SET "
                        + "class=" + str_Class + ", "
                        + "content=" + str_Content + ", "
                        + "money=" + str_Money
                        + " WHERE "
                        + "year=" + year + " AND month=" + month + " AND day=" + day + " AND class=" + aclass + " AND content=" + content + " AND money=" + money
            )
            sqlitedb.close()
            dialog.dismiss()
        }

        deleteBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase
            sqlitedb.execSQL("DELETE FROM "+acc+"" +
                    " WHERE "
                    + "year=" + year + " AND month=" + month + " AND day=" + day + " AND class=" + aclass + " AND content=" + content + " AND money=" + money)
            sqlitedb.close()
            dialog.dismiss()
        }


    }
}