package pt.atp.playground

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AlertDialogLayout
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.lang.ref.ReferenceQueue

private const val REQUEST_IMAGE_CAPTURE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 10000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = getIntent()
        val greetings = intent.getStringExtra("OLA")
        findViewById<TextView>(R.id.greetings).setText("Hello " +greetings)


        findViewById<Button>(R.id.btn_show).setOnClickListener {
            val snackbar = Snackbar.make(it, "Welcome", Snackbar.LENGTH_INDEFINITE )
            snackbar.setAction("Parar", View.OnClickListener {
                timer.cancel()
            })
//            snackbar.setAction("Começar", View.OnClickListener {
//                startcountDownTimer(untilFinished)
//            })
            snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.black))
            snackbar.show()
        }
//        findViewById<Button>(R.id.btn_show).setOnClickListener {
//            //openNativeCamera()
//            //openDetailsActivity()
//            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//            builder.setTitle("Show Simple Dialog")
//            builder.setMessage("This Is A Simple Alert Dialog Show In Android")
//            builder.setIcon(R.drawable.ic_launcher_background)
//
//            builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
//                openDetailsActivity()
//            })
//            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
//                dialog.dismiss()
//            })
//            builder.setNeutralButton("No", DialogInterface.OnClickListener { dialog, which ->
//                dialog.dismiss()
//            })
//
//            val alertDialog: AlertDialog = builder.create()
//            alertDialog.show()
//        }
    }

    override fun onResume() {
        super.onResume()

        startcountDownTimer(untilFinished)
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView3).setImageBitmap(imageBitmap)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openNativeCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openDetailsActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }



    private fun startcountDownTimer(time: Long) {
        timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                untilFinished = millisUntilFinished
                findViewById<TextView>(R.id.coutdown).text  = "Seconds remaining: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                findViewById<TextView>(R.id.coutdown).text  = "Done"
            }

        }

        timer.start()
    }

    fun showSimpleDialog(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Show Simple Dialog")
        builder.setMessage("This Is A Simple Alert Dialog Show In Android")
        builder.setIcon(R.drawable.ic_launcher_background)

        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        builder.setNeutralButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}