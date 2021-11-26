package com.example.todo_list

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_main)

        auth = FirebaseAuth.getInstance()
        val Email2 = findViewById<EditText>(R.id.email2)
        val passWord2 = findViewById<EditText>(R.id.password2)
        val passWord3 = findViewById<EditText>(R.id.password3)
        val register_btn = findViewById<Button>(R.id.register_btn)

        register_btn.setOnClickListener{
            val email2: String = Email2.text.toString().trim()
            val password2: String = passWord2.text.toString().trim()
            val password3: String = passWord3.text.toString().trim()

            if (email2.isEmpty()) {
                Email2.error = "Email harus diisi"
                Email2.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
                Email2.error = "Email tidak valid"
                Email2.requestFocus()
                return@setOnClickListener
            }

            if (password2.isEmpty() && password2.length < 8) {
                passWord2.error = "Password harus diisi lebih dari 8 karakter"
                passWord2.requestFocus()
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Registering...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            registerUser(email2, password2)
        }
        val loginClick = findViewById<TextView>(R.id.login_click)
        loginClick.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(email2: String, password2: String) {
        auth.createUserWithEmailAndPassword(email2, password2)
            .addOnCompleteListener(this) {
            if(it.isSuccessful) {
                Intent(this@RegisterActivity, LoginActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
            }
        } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}