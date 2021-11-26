package com.example.todo_list

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text

class KegiatanAdapter(private val kegiatanList : ArrayList<Kegiatan>) :
    RecyclerView.Adapter<KegiatanAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = kegiatanList[position]

        holder.nama_keg.text = currentitem.nama_keg
        holder.deskripsi_keg.text = currentitem.deskripsi_keg
    }

    override fun getItemCount(): Int {
        return kegiatanList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nama_keg: TextView = itemView.findViewById(R.id.nama_keg)
        val deskripsi_keg: TextView = itemView.findViewById(R.id.deskripsi_keg)
        }
    }
