package com.gog.tareco

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

private const val REQUEST_IMAGE_CAPTURE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 10000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra("username")

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.tv_username).apply {
            text = message
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
    }

    override fun onResume() {
        super.onResume()
       /* val intent = Intent(this, MainActivity::class.java)
        //val intent = getIntent()
        val username = intent.getStringExtra("username")
        findViewById<TextView>(R.id.tv_username).text = "Hello $username"
        */

        startCountDownTimer(untilFinished)
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

    private fun openNativeCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openDetailsActivity(){
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    private fun startCountDownTimer(time: Long){
        timer = object: CountDownTimer(time, 1000){

            override fun onTick(millisUntilFinished: Long) {
                untilFinished = millisUntilFinished
                findViewById<TextView>(R.id.countdown).text = "Seconds remaining: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                findViewById<TextView>(R.id.countdown).text = "Done!"
            }

        }

        timer.start()
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
}