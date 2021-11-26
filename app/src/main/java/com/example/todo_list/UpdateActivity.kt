package com.example.todo_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity: AppCompatActivity() {

    private lateinit var ref : DatabaseReference
    private lateinit var nama_keg : EditText
    private lateinit var deskripsi_keg : EditText
    private lateinit var update_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_main)

        nama_keg = findViewById(R.id.et_nama_update)
        deskripsi_keg = findViewById(R.id.et_deskripsi_update)
        update_button = findViewById(R.id.update_button)

        update_button.setOnClickListener {
            val keg_nama = nama_keg.text.toString()
            val keg_deskripsi = deskripsi_keg.text.toString()

            if (keg_nama.isEmpty()) {
                nama_keg.error = "Please enter your item name!"
                return@setOnClickListener
            }
            if (keg_deskripsi.isEmpty()) {
                deskripsi_keg.error = "Please enter your item description!"
                return@setOnClickListener
            }
            updateData(keg_nama, keg_deskripsi)
        }



        val cancel_button: Button = findViewById(R.id.cancel_button3)
        cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
            }

    private fun updateData(keg_nama: String, keg_deskripsi: String) {
        ref = FirebaseDatabase.getInstance().getReference("Kegiatan")
        val Transaction = mapOf<String, String>(
            "nama_keg" to keg_nama,
            "deskripsi_keg" to keg_deskripsi
        )

        ref.child(keg_nama).updateChildren(Transaction).addOnSuccessListener {
            Toast.makeText(this, "Kegiatan berhasil diupdate", Toast.LENGTH_SHORT).show()
            nama_keg.text.clear()
            deskripsi_keg.text.clear()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Update kegiatan gagal", Toast.LENGTH_SHORT).show()
        }
    }
}

