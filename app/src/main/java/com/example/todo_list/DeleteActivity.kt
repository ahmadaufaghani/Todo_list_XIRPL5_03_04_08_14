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

class DeleteActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var nama_keg: EditText
    private lateinit var delete_button : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_main)

        nama_keg = findViewById(R.id.et_nama_delete)
        delete_button = findViewById(R.id.delete_button)

        val cancel_button: Button = findViewById(R.id.cancel_button4)
        cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        delete_button.setOnClickListener{

            val keg_nama = nama_keg.text.toString()
            if (keg_nama.isNotEmpty()) {
                deleteKegiatan(keg_nama)
            }
            else {
                Toast.makeText(this, "Tologn masukkan nama kegiatan!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deleteKegiatan(keg_nama: String) {

        dbref = FirebaseDatabase.getInstance().getReference("Kegiatan")
        dbref.child(keg_nama).removeValue().addOnSuccessListener {
            nama_keg.text.clear()
            Toast.makeText(this, "Kegiatan berhasil dihapus!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this, "Kegiatan gagal dihapus", Toast.LENGTH_SHORT).show()
        }

    }
}
