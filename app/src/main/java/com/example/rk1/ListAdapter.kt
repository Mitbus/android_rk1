package com.example.rk1

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
    var data = mutableListOf<Info>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun addData(info: PostModel?) {
        val text =  info?.elementPureHtml.toString()
        val headerLen = 30
        val head = "${text.substring(0, headerLen)}..."
        val tail = if (text.length > headerLen) text else ""
        data.add(Info(head, tail))

    }

    override fun getItemCount() = data.size

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hideMore()
        holder.setText(data[position])
    }
}