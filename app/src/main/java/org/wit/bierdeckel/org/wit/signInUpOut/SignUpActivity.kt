package org.wit.bierdeckel.org.wit.signInUpOut

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.wit.bierdeckel.databinding.ActivitySignUpBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.userModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var uID: String
    private lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val androidId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.alreadyRegisteredText.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            // Beim Registrieren wird der Bierdeckel (debtModel) erzeugt und in die Liste in der DB gescrhieben, sowie dem User nochmals übergeben
                            database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
                            uID= firebaseAuth.currentUser?.uid!!


                            var userSchulden = debtModel("", "", 0.0, uID)
                            var user= userModel("","","","",email,"",uID,"","", userSchulden)
                            database.child("Schulden").child(uID).setValue(userSchulden)
                            database.child("User").child(uID).setValue(user)
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Passwort stimmt nicht überein!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Keine leeren Felder!", Toast.LENGTH_SHORT).show()

            }
        }
    }


}