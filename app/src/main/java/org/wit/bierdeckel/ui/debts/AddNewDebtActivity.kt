package org.wit.bierdeckel.ui.debts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import androidx.navigation.*
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.databinding.ActivityAddNewDebtBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel

class AddNewDebtActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNewDebtBinding
    private lateinit var app: MainApp
    private lateinit var currentDebt : debtModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewDebtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        currentDebt = debtModel("", "", 0.0)




        binding.buttonCustomDebtAnlegen.setOnClickListener(){





        }


        binding.buttonCustomDebtAbbrechen.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }



    }
}