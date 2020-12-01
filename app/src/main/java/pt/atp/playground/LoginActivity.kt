package pt.atp.playground

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setup()
    }

    private fun setup() {
        findViewById<Button>(R.id.btn_auth).setOnClickListener {
            areCredentialsValid()
//            if(areCredentialsValid()) {
//                val intent = Intent(this, MainActivity::class.java)
//                val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
//
//                intent.putExtra("OLA", username)
//                startActivity(intent)
//                finish()
//            }
        }

        findViewById<EditText>(R.id.editTextTextPersonName).setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                areCredentialsValid()
//                if (areCredentialsValid()) {
//                    val intent = Intent(this, MainActivity::class.java)
//                    val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
//
//                    intent.putExtra("OLA", username)
//                    startActivity(intent)
//                    finish()
//                }
            }
            true
        }

        findViewById<EditText>(R.id.editTextTextPassword).setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                areCredentialsValid()
//                if (areCredentialsValid()) {
//                    val intent = Intent(this, MainActivity::class.java)
//                    val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
//
//                    intent.putExtra("OLA", username)
//                    startActivity(intent)
//                    finish()
//                }
            }
            true
        }

        viewModel.loginResultLiveData.observe(this){ loginResult ->
            if (!loginResult) {
                findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            } else {
                val intent = Intent(this, MainActivity::class.java)
                val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

                intent.putExtra("OLA", username)
                startActivity(intent)
                finish()
            }

        }

    }

    private fun areCredentialsValid() {
        val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        if (username.isEmpty()) {
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return
        }

        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        if (password.isEmpty()) {
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return
        }

        viewModel.areCredentialsValid(username, password)
//        if (username != password) {
//            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
//            return false
//        } else {
//            return username == password
//        }

    }

}