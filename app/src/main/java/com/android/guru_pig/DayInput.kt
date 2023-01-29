package com.android.guru_pig

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

class DayInput : AppCompatActivity() {

    lateinit var dbManger: DBManger
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var rg_account : RadioGroup
    lateinit var rb_plus : RadioButton
    lateinit var rb_minus : RadioButton
    lateinit var edtClass : EditText
    lateinit var edtMoney : EditText
    lateinit var edtContent : EditText
    lateinit var inputBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_input)

        rg_account=findViewById(R.id.differ)
        rb_plus = findViewById(R.id.plus)
        rb_minus = findViewById(R.id.minus)
        edtClass = findViewById(R.id.edtClass)
        edtMoney = findViewById(R.id.edtMoney)
        edtContent = findViewById(R.id.edtContent)
        inputBtn = findViewById(R.id.btnInput)

        dbManger = DBManger(this, "accountDB", null, 1)

        inputBtn.setOnClickListener{

            var str_account: String = ""
            if (rg_account.checkedRadioButtonId == R.id.plus) {
                str_account = rb_plus.text.toString()
            }
            if (rg_account.checkedRadioButtonId == R.id.minus) {
                str_account = rb_minus.text.toString()
            }

            var str_Class: String = edtClass.text.toString()
            var str_Money: String = edtMoney.text.toString()
            var str_Content: String = edtContent.text.toString()

            sqlitedb = dbManger.writableDatabase
            sqlitedb.execSQL("INSERT INTO plus VALUES('"+str_account+"','"+str_Class+"','"+str_Money+"','"+str_Content+"')")
            sqlitedb.close()

            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("intent_accout",str_Class)
            startActivity(intent)
        }
    }
}