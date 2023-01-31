package com.android.guru_pig

import android.app.Dialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.guru_pig.MyApp.Companion.applicationContext

class DayDialog(context: Context) {
    private val dialog = Dialog(context)

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    //내역 입력하는 다이얼로그
    fun showDialog(year: Int, month: Int, day: Int, acc: String){
        dialog.setContentView(R.layout.day_input)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val spinClass = dialog.findViewById<Spinner>(R.id.spinClass)
        val edtMoney = dialog.findViewById<EditText>(R.id.edtMoney)
        val edtContent = dialog.findViewById<EditText>(R.id.edtContent)
        val inputBtn = dialog.findViewById<Button>(R.id.btnInput)
        val cancelBtn = dialog.findViewById<Button>(R.id.btnCancel)


        val acontext: Context = applicationContext()
        dbManger = DBManger(acontext, "accountDB", null, 1)

        //수입 수출에 따라서 스피너 아이템 목록 출력
        if (acc == "plus") {
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

        //입력 버튼
        inputBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase

            var str_Money: String = edtMoney.text.toString()
            var str_Content: String = edtContent.text.toString()

            sqlitedb.execSQL("INSERT INTO "+acc+" VALUES('"+year+"','"+month+"','"+day+"','"+str_Class+"','"+str_Money+"','"+str_Content+"')")
            sqlitedb.close()
            dialog.dismiss()
        }

        //취소 버튼
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    //내역 수정 및 삭제하는 다이얼로그
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


        var plusArray = arrayOf("월급","부수입","용돈","상여금","금융소득","기타")
        var minusArray = arrayOf("식비","교통","문화생활","편의생활","패션/미용","주거/통신","건강","교육","경조사/선물","반려동물","기타")

        val acontext: Context = applicationContext()
        dbManger = DBManger(acontext, "accountDB", null, 1)

        //수입 수출에 따라서 스피너 아이템 목록 출력 및 기존 값 선택
        if (acc == "plus") {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.plusList, android.R.layout.simple_spinner_dropdown_item)
            spinClass.setSelection(plusArray.indexOf(aclass))
        }else {
            spinClass.adapter = ArrayAdapter.createFromResource(applicationContext(), R.array.minusList, android.R.layout.simple_spinner_dropdown_item)
            spinClass.setSelection(minusArray.indexOf(aclass))
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

        edtMoney.setText(money)
        edtContent.setText(content)

        //수정 버튼
        updateBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase

            var str_Money: String = edtMoney.text.toString()
            var str_Content: String = edtContent.text.toString()

            sqlitedb.execSQL(
                "UPDATE " + acc
                        + " SET "
                        + "class='" + str_Class + "', "
                        + "content='" + str_Content + "', "
                        + "money=" + str_Money
                        + " WHERE "
                        + "year="+year+" AND month="+month+" AND day="+day+" AND class='"+aclass+"' AND content='"+content+"' AND money="+money
            )
            sqlitedb.close()
            dialog.dismiss()
        }

        //삭제 버튼
        deleteBtn.setOnClickListener {
            sqlitedb = dbManger.writableDatabase
            sqlitedb.execSQL("DELETE FROM "+acc+" WHERE "
                    +"year="+year+" AND month="+month+" AND day="+day+" AND class='"+aclass+"' AND content='"+content+"' AND money="+money)
            sqlitedb.close()
            dialog.dismiss()
        }

        dialog.show()
    }
}