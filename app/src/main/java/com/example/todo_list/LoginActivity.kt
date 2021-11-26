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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        auth = FirebaseAuth.getInstance()
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login_btn = findViewById<Button>(R.id.login_btn)

        login_btn.setOnClickListener{
            val email1: String = email.text.toString().trim()
            val password1: String = password.text.toString().trim()

            if (email1.isEmpty()) {
                email.error = "Email harus diisi"
                email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                email.error = "Email tidak valid"
                email.requestFocus()
                return@setOnClickListener
            }

            if (password1.isEmpty() && password1.length < 8) {
                password.error = "Password harus diisi lebih dari 8 karakter"
                password.requestFocus()
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Logging In...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            loginUser(email1, password1)
        }

        val registerClick = findViewById<TextView>(R.id.register_click)
        registerClick.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                        intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}