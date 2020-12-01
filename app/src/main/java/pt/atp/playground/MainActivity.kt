package pt.atp.playground

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image

import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore

import android.view.View
import android.view.textclassifier.TextClassifierEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.ReferenceQueue
import androidx.lifecycle.*
import org.intellij.lang.annotations.Language


private const val REQUEST_IMAGE_CAPTURE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 10000L

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = getIntent()
        val greetings = intent.getStringExtra("OLA")
        findViewById<TextView>(R.id.greetings).setText("Hello " +greetings)


        findViewById<Button>(R.id.btn_show).setOnClickListener {
            findViewById<TextView>(R.id.coutdown).visibility = View.VISIBLE
            startTimer()
        }

        findViewById<Button>(R.id.btn_counter).setOnClickListener {
            findViewById<TextView>(R.id.coutdown).visibility = View.VISIBLE
            startcountDownTimer(untilFinished)
        }

        val tvStartTimer = findViewById<TextView>(R.id.coutdown)
        viewModel.timerLiveData.observe(this) { counter ->
            tvStartTimer.text = counter.toString()
        }
//            val snackbar = Snackbar.make(it, "Welcome", Snackbar.LENGTH_INDEFINITE )
//            snackbar.setAction("Parar", View.OnClickListener {
//                timer.cancel()
//            })
////            snackbar.setAction("Começar", View.OnClickListener {
////                startcountDownTimer(untilFinished)
////            })
//            snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.black))
//            snackbar.show()
//        }

        findViewById<Button>(R.id.SimpleDialog).setOnClickListener{
            showSimpleDialog()
        }

        findViewById<Button>(R.id.details).setOnClickListener {
            openDetailsActivity()
        }

        findViewById<Button>(R.id.snackbar).setOnClickListener {
            showSnackbar()
        }

        findViewById<Button>(R.id.camera).setOnClickListener {
            openNativeCamera()
        }
    }

    override fun onResume() {
        super.onResume()

        if (untilFinished < 10000L) {
            startcountDownTimer(untilFinished)
        }
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

    private fun showSimpleDialog() {
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

    private fun showSnackbar() {
        Snackbar.make(
            findViewById<ConstraintLayout>(R.id.root_layout),
            R.string.snackbar_main,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.snackbar_btn) {
                Toast.makeText(
                    this@MainActivity,
                        (R.string.snackbar_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()

//        val snackbar = Snackbar.make(it, "Welcome", Snackbar.LENGTH_INDEFINITE )
//        snackbar.setAction("Parar", View.OnClickListener {
//            timer.cancel()
//        })
////            snackbar.setAction("Começar", View.OnClickListener {
////                startcountDownTimer(untilFinished)
////            })
//        snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.black))
//        snackbar.show()
    }

    private fun startTimer() {
        viewModel.startTimer(15000)


    }
}