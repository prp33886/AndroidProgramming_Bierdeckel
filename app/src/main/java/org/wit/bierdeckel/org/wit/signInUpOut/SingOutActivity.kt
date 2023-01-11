package org.wit.bierdeckel.org.wit.signInUpOut

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.wit.bierdeckel.databinding.ActivityLogOutBinding
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.R


class SingOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogOutBinding
    private lateinit var auth: FirebaseAuth;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogOutBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonLogout.setOnClickListener(){


            Snackbar
                .make(it,"Logout erfolgreich", Snackbar.LENGTH_LONG)
                .show()

            auth = Firebase.auth
            Firebase.auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

        }

        binding.buttonAbbrechen.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }


}


