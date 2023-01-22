package org.wit.bierdeckel.org.wit.signInUpOut

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.bierdeckel.databinding.ActivityLogOutBinding
import org.wit.bierdeckel.MainActivity


class SingOutActivity : AppCompatActivity() {

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

        binding.button3.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }




    }
}