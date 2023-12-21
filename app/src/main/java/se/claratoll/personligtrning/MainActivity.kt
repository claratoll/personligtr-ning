package se.claratoll.personligtrning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        emailView = findViewById(R.id.editTextTextPersonName)
        passwordView = findViewById(R.id.editTextTextPassword)

        if (auth.currentUser != null){
            startApp()
        }

        val loginButton = findViewById<Button>(R.id.loginbutton)
        loginButton.setOnClickListener {
            logIn()
        }

        val forgotPasswordButton = findViewById<Button>(R.id.forgotpasswordbutton)
        forgotPasswordButton.setOnClickListener {
            forgotpassword()
        }

    }

    private fun startApp() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }

    private fun forgotpassword() {
        email = emailView.text.toString()

        if (email.isEmpty()) {
            // Handle the empty email case in the fragment
            Toast.makeText(this, "Skriv din mail först", Toast.LENGTH_SHORT).show()
        } else {
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("!!!", "Email sent.")
                        Toast.makeText(this, "Email skickat med länk så du kan återställa ditt lösenord", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun logIn() {
        email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        if (password.length < 6) {
            throw ArithmeticException("Password is too short")
        } else {
            println("Strong password")
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("!!!", "login success")
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                startApp()
            } else {
                Log.d("!!!", "user not logged in ${task.exception}")
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}