package com.example.todo_list

import android.app.ProgressDialog
import android.content.Intent
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Kegiatan>
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val logout_btn = findViewById<ImageButton>(R.id.logout_btn)
        logout_btn.setOnClickListener {
            auth.signOut()
            Intent(this@MainActivity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        val floating_btn = findViewById<FloatingActionButton>(R.id.floating_btn_add)
        floating_btn.setOnClickListener{
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        userRecyclerView = findViewById(R.id.kegiatan_list)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<Kegiatan>()
        getKegiatanData()

        val deletebtn: ImageButton = findViewById(R.id.delete_button1)
        deletebtn.setOnClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

        val updatebtn: ImageButton = findViewById(R.id.updatebtn)
        updatebtn.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getKegiatanData() {
        dbref = FirebaseDatabase.getInstance().getReference("Kegiatan")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                   if(snapshot.exists()) {
                        for (kegiatanSnapshot in snapshot.children) {
                            val kegiatan = kegiatanSnapshot.getValue(Kegiatan::class.java)
                            userArrayList.add(kegiatan!!)
                        }
                        userRecyclerView.adapter = KegiatanAdapter(userArrayList)
                   }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}