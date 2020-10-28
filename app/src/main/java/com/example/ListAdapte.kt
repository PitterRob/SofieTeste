package com.example

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.sofie.R
import com.example.sofie.models.ListaTarefas



class ListAdapte(val context: Context, val list: ArrayList<ListaTarefas>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view: View = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)

        val title = view.findViewById(R.id.title) as AppCompatTextView
        val description = view.findViewById(R.id.description) as AppCompatTextView

        title.text = list[position].title
        description.text = list[position].description
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }


}