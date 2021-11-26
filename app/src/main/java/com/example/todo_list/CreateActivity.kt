package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateActivity : AppCompatActivity() {

    private lateinit var ref : DatabaseReference
    private lateinit var nama_keg : EditText
    private lateinit var deskripsi_keg : EditText
    private lateinit var insert_btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_main)

        nama_keg = findViewById(R.id.et_nama)
        deskripsi_keg = findViewById(R.id.et_deskripsi)
        insert_btn = findViewById(R.id.insert_button)

        insert_btn.setOnClickListener{
            val nama_keg1 = nama_keg.text.toString()
            val deskripsi_keg1 = deskripsi_keg.text.toString()

            if (nama_keg1.isEmpty()) {
                nama_keg.error = "Tolong masukkan nama kegiatan!"
                return@setOnClickListener
            }
            if (deskripsi_keg1.isEmpty()) {
                deskripsi_keg.error = "Tolong masukkan deskripsi kegiatan!"
                return@setOnClickListener
            }


            ref = FirebaseDatabase.getInstance().getReference("Kegiatan")
            val kegiatan = Kegiatan(nama_keg1, deskripsi_keg1)

            ref.child(nama_keg1).setValue(kegiatan).addOnSuccessListener {
                Toast.makeText(this, "Kegiatan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                nama_keg.text.clear()
                deskripsi_keg.text.clear()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Kegiatan gagal ditambahkan", Toast.LENGTH_SHORT).show()
            }

        }

        val cancel_button: Button = findViewById(R.id.cancel_button1)
        cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}