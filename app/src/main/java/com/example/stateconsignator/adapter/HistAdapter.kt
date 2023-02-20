package com.example.stateconsignator.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stateconsignator.R
import com.example.stateconsignator.data.Hist

class HistAdapter (
    val mContext : Context,
    val datas : List<Hist>,
    val resources : Resources
) : RecyclerView.Adapter<HistAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val date =  itemView.findViewById<TextView>(R.id.tv_hist_date)
        val heure =  itemView.findViewById<TextView>(R.id.tv_hist_hour)
        val etat =  itemView.findViewById<TextView>(R.id.tv_hist_state)
        val indicateur = itemView.findViewById<ImageView>(R.id.hist_state_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_history,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = datas [position]
        holder.date.text = item.date
        holder.heure.text = item.heure

        if (item.etat == "1") {
            holder.indicateur.setColorFilter(resources.getColor(R.color.green))
            holder.etat.text = "ON"
        }
        else  {
            holder.indicateur.setColorFilter(resources.getColor(R.color.red))
            holder.etat.text = "OFF"
        }
    }

    override fun getItemCount(): Int {
        return  datas.size
    }
}