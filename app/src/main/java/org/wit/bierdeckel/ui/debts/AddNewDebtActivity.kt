package org.wit.bierdeckel.ui.debts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import androidx.navigation.*
import com.google.android.material.textfield.TextInputEditText
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.databinding.ActivityAddNewDebtBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel

class AddNewDebtActivity : AppCompatActivity() {

    // Adding Custom Debts for Guests - not registrated

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

            var vorName = binding.customDebtVorNameText.text.toString()
            var nachName = binding.customDebtNachNameText.text.toString()
            var schulden : Double = binding.customDebtAmountText.text.toString().toDouble()

            var custom = debtModel(vorName, nachName, schulden)

            app.schulden.add(custom)

        }


        binding.buttonCustomDebtAbbrechen.setOnClickListener(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }



    }
}