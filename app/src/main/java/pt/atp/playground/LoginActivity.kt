package pt.atp.playground

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setup()
    }

    private fun setup() {
        findViewById<Button>(R.id.btn_auth).setOnClickListener {

            if (areCredentialsValid()) {
                val intent = Intent(this, MainActivity::class.java)
                val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                intent.putExtra("OLA", username)
                startActivity(intent)
                finish()
            }
        }

        findViewById<EditText>(R.id.editTextTextPersonName).setOnEditorActionListener { _, actionId , _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                areCredentialsValid()
                if (areCredentialsValid()) {
                    val intent = Intent(this, MainActivity::class.java)
                    val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    intent.putExtra("OLA", username)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        findViewById<EditText>(R.id.editTextTextPassword).setOnEditorActionListener { _, actionId , _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                areCredentialsValid()
                if (areCredentialsValid()) {
                    val intent = Intent(this, MainActivity::class.java)
                    val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    intent.putExtra("OLA", username)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }



    private fun areCredentialsValid(): Boolean {
        val username = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        if (username.isEmpty()) {
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return false
        }
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        if (password.isEmpty()) {
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return false
        }

        if (username != password){
            findViewById<TextView>(R.id.tv_error).visibility = View.VISIBLE
            return false
        } else {
            return username  == password
        }

    }

}