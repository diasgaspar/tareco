package com.gog.tareco

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import java.io.File

private const val REQUEST_IMAGE_CAPTURE = 100
private const val REQUEST_READ_STORAGE = 500

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private val viewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get the Intent that started this activity and extract the string
       // val message = intent.getStringExtra("username")
        // Capture the layout's TextView and set the string as its text
        findViewById<TextView>(R.id.tv_username).apply {
            text = intent.getStringExtra("username")
        }



        findViewById<Button>(R.id.button_details).setOnClickListener{
            //openNativeCamera()
            openDetailsActivity()
            //openAlertDialogBox()
            //openSnackBar()
        }

        findViewById<Button>(R.id.button_alert).setOnClickListener{
            //openNativeCamera()
            //openDetailsActivity()
            openAlertDialogBox()
            //openSnackBar()
        }

        findViewById<Button>(R.id.button_snackbar).setOnClickListener{
            openSnackBar()
        }

        findViewById<Button>(R.id.button_start_timer).setOnClickListener{
            startTimer()
        }
        val tvStartTimer = findViewById<TextView>(R.id.tv_countdown)
        viewModel.timerLiveData.observe(this) {
            tvStartTimer.text= it.toString()
            if(it == 0L){
                loadImage()
            }
        }


    }


    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    //apresenta a foto que foi tirada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitMap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitMap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        if(requestCode == REQUEST_READ_STORAGE){
            if(permissions[0]== android.Manifest.permission.READ_EXTERNAL_STORAGE &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //loadImage()
                startTimer()

            }
        }
        else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    private fun openNativeCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openDetailsActivity(){
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }


    private fun openAlertDialogBox(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes", null)
        //performing cancel action
        builder.setNeutralButton("Cancel", null)
        //performing negative action
        builder.setNegativeButton("No", null)
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
        //val intent = Intent(this, AlertDialog::class.java)
        //startActivity(intent)

    }

    private fun openSnackBar(){
        Snackbar.make(
                findViewById<ConstraintLayout>(R.id.container),
                R.string.snackbar_message,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action_thanks) {
                    Toast.makeText(this@MainActivity, R.string.snackbar_action_thanks_selected, Toast.LENGTH_SHORT).show()
                }
                .show()

    }

    private fun startTimer(){
        if(!checkPermissionAndRequest()){
            return
        }

        viewModel.starTimer(5000)
    }

    private fun checkPermissionAndRequest(): Boolean{
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE)
            return false
        }
        return true
    }

    private fun loadImage(){
        val file = File("/storage/emulated/0/Download/239664-1604x988-snail.jpg")
        val uri = Uri.fromFile(file)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(uri)
    }
}