package com.android.guru_pig

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closing, container, false)

        dbManger = DBManger(getActivity(), "closeDB", null, 1)
        sqlitedb = dbManger.readableDatabase
    }


}