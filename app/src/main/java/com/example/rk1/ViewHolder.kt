package com.example.rk1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rk1.R


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val listItemViewDescription = itemView.findViewById<TextView>(R.id.description_item)
    private val listItemViewHeader = itemView.findViewById<TextView>(R.id.tv_item)

    fun setText(data: Info) {
        listItemViewDescription.text = data.description
        listItemViewHeader.text = data.header
    }

    fun isHidden(): Boolean {
        return listItemViewDescription.visibility == View.GONE
    }

    fun showMore() {
        listItemViewDescription.visibility = View.VISIBLE
    }

    fun hideMore() {
        listItemViewDescription.visibility = View.GONE
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            val viewHolder = ViewHolder(view)
            view.setOnClickListener {
                if (viewHolder.isHidden())
                    viewHolder.showMore()
                else
                    viewHolder.hideMore()
            }
            return viewHolder
        }
    }
}