package org.wit.bierdeckel.org.wit.signInUpOut

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.databinding.ActivitySignInBinding
import org.wit.bierdeckel.main.MainApp

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var app: MainApp




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        app = application as MainApp

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val passwort = binding.passET.text.toString()

            if (email.isNotEmpty() && passwort.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, passwort).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        Toast.makeText(this, "Erfolgreich eingeloggt!", Toast.LENGTH_LONG)
                        startActivity(intent)


                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Keine leeren Felder!!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // Eingeloggt bleiben beim starten
    /*override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    */

}