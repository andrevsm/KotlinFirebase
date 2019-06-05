package com.positivo.kotlinfbtest.model

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.positivo.kotlinfbtest.R

class TesterAdapter (
    var listTester: ArrayList<Tester>
) :
    Adapter<TesterAdapter.TesterViewHolder>() {

    override fun getItemCount(): Int {
        return listTester.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TesterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tester_adapter, parent, false)
        val holder = TesterViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: TesterViewHolder, position: Int) {
        createHolder(holder, position)
    }

    fun createHolder(holder: TesterViewHolder, position: Int){
        var tester = listTester[position]
        holder.name.text = tester.name
        holder.codigo.text = tester.codigo
    }


    class TesterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var codigo: TextView

        init {
            name = view.findViewById(R.id.name)
            codigo = view.findViewById(R.id.codigo)
        }
    }
}