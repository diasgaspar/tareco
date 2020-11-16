package com.gog.tareco

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setup()
    }

    private fun setup(){
        findViewById<Button>(R.id.btn_auth).setOnClickListener{

            if(areCredentialValid()){
                val intent = Intent(this, MainActivity::class.java)
                val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                intent.putExtra("username", "username")
                startActivity(intent)
                finish()
            }
        }
    }

    private fun areCredentialValid():Boolean{
        val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        if (username.isEmpty()){
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return false

        }
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        if (password.isEmpty()){
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return false
        }

        return username == password
    }
}