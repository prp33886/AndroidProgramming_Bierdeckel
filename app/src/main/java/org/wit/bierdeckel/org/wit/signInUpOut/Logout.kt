package org.wit.bierdeckel.org.wit.signInUpOut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.wit.bierdeckel.R
import org.wit.bierdeckel.databinding.ActivityLogOutBinding
import org.wit.bierdeckel.databinding.ActivitySignInBinding


class Logout : AppCompatActivity() {

    private lateinit var binding: ActivityLogOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogOutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLogout.setOnClickListener(){


            Snackbar
                .make(it,"Logout!!!!!!!", Snackbar.LENGTH_LONG)
                .show()

        }


    }
}