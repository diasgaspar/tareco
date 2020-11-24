package com.gog.tareco

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

class LoginActivity : AppCompatActivity(){

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

      //viewModel = ViewModelProvider( this ).get(LoginViewModel::class.java)


        setup()
    }

    private fun setup(){


        findViewById<Button>(R.id.btn_auth).setOnClickListener{

           validateCredentialsAndRedirect()
        }
        viewModel.loginResultLiveData.observe(this){ loginResult ->
                if(!loginResult){
                    findViewById<TextView>(R.id.tv_error).text = getString(R.string.error_invalid_credentials)
                }else{
                    val username:String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    val intent = Intent(this, MainActivity::class.java)

                    intent.putExtra("username", username)
                    startActivity(intent)
                    finish()
                }
            }


    }

    private fun validateCredentialsAndRedirect(){

        val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        if (username.isEmpty()){
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return

        }
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        if (password.isEmpty()){
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return
        }

        viewModel.areCredentialValid(username,password)


    }

}