package com.example.rk1

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter

class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
    var data = mutableListOf<Info>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    val datePart = 10

    fun addData(dataRow: DataRow?) {
        val date = dataRow?.time?.let {
            "date: ${DateTimeFormatter.ISO_INSTANT.format(
                Instant.ofEpochSecond(it.toLong())
            ).substring(0, datePart)}\n"
        }
        val avg = (dataRow?.low?.let {
            dataRow.high?.toDouble()?.plus(it.toDouble())?.div(2)
        })?.let {
            "average value: %.3f\n".format(it)
        }

        val low = dataRow?.low?.let {"min value: $it\n"}
        val high = dataRow?.high?.let {"max value: $it\n"}
        val open = dataRow?.open?.let {"opened value: $it\n"}
        val close = dataRow?.close?.let {"closed value: $it\n"}
        val from = dataRow?.volumeFrom?.let {"volume from: $it\n"}
        val to = dataRow?.volumeTo?.let {"volume to: $it\n"}

        val head = "$date$avg"
        val tail = "$date$avg$low$high$open$close$from$to"
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