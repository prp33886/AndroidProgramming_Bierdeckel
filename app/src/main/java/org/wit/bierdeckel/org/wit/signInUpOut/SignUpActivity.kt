package org.wit.bierdeckel.org.wit.signInUpOut

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.wit.bierdeckel.databinding.ActivitySignUpBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.userModel
import java.security.KeyStore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var uID: String
    private lateinit var app:MainApp
    private lateinit var spinner : Spinner
    private var barName : String = ""
    private var options : ArrayList<String> = arrayListOf<String>("Wähle deine Bar aus!")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val androidId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        firebaseAuth = FirebaseAuth.getInstance()

        spinner = binding.barSpinner as Spinner

        getBarArray() ;


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,options)
        spinner.adapter = arrayAdapter



        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                barName=options[position]

                Toast.makeText(applicationContext, "Ausgewählte bar: "+options[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Bitte wähle deine Bar aus!", Toast.LENGTH_SHORT).show()
            }

        }




        binding.alreadyRegisteredText.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && barName!="Wähle deine Bar aus!"&& barName!="") {
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
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }




    }

    private fun getBarArray() {

        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        val ref = database.child("Bars")


        //Snapshot holen und die Liste auswerten
            ref.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (stringSnapshot in dataSnapshot.children) {

                    val string = stringSnapshot.getValue(String::class.java)

                    if (string != null) {

                        options.add(string)

                    }else{

                        options.add("Default")

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Fehler beim Barliste holen " + databaseError.code)
            }
        })



    }


}