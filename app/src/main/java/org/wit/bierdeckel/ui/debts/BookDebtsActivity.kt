package org.wit.bierdeckel.ui.debts

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.updateBounds
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.R
import org.wit.bierdeckel.databinding.ActivityBookDebtsBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.priceListModel

class BookDebtsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDebtsBinding
    private lateinit var app: MainApp
    private lateinit var currentDebt : debtModel
    private var amount = 0.0
    private var priceList : priceListModel = priceListModel("", ArrayList<String>())


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDebtsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        if(actionBar != null)
        {
            actionBar.title="BookNewDebts"
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)


        app = application as MainApp
        priceList = app.pricelist

        val bundle : Bundle?=intent.extras
        val result : debtModel? = bundle?.getSerializable("bierdeckel", debtModel::class.java)

        currentDebt = result!!



        // 0 Beer
        var beerAmount = 0.0

        binding.buttonPlusBeer.setOnClickListener(){
            println(beerAmount)
            println(amount)
            beerAmount+= priceList.pricelist.get(0).toDouble()
            amount += priceList.pricelist.get(0).toDouble()

            binding.amountBeer.text = beerAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        binding.buttonMinusBeer.setOnClickListener(){
            println(beerAmount)
            println(amount)
            beerAmount-= priceList.pricelist.get(0).toDouble()
            amount -= priceList.pricelist.get(0).toDouble()

            binding.amountBeer.text = beerAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        // 1 Longdrink
        var longDrinkAmount = 0.0


        binding.buttonPlusLongdrink.setOnClickListener(){
            longDrinkAmount+= priceList.pricelist.get(1).toDouble()
            amount += priceList.pricelist.get(1).toDouble()

            binding.amountLongDrink.text = longDrinkAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        binding.buttonMinusLongdrink.setOnClickListener(){
            longDrinkAmount-= priceList.pricelist.get(1).toDouble()
            amount -= priceList.pricelist[1].toDouble()

            binding.amountLongDrink.text = longDrinkAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        // 2 nonAlcoholic

        var nonAlcoholicAmount = 0.0

        binding.buttonPlusNonalcoholic.setOnClickListener(){
            nonAlcoholicAmount+= priceList.pricelist.get(2).toDouble()
            amount += priceList.pricelist[2].toDouble()

            binding.amountNonalcoholic.text = nonAlcoholicAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        binding.buttonMinusNonalcoholic.setOnClickListener(){
            nonAlcoholicAmount-= priceList.pricelist.get(2).toDouble()
            amount -= priceList.pricelist[2].toDouble()

            binding.amountNonalcoholic.setText(nonAlcoholicAmount.toString())
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }


        // 3 Food

        var foodAmount = 0.0

        binding.buttonPlusFood.setOnClickListener(){
            foodAmount+= priceList.pricelist.get(3).toDouble()
            amount += priceList.pricelist.get(3).toDouble()

            binding.amountFood.text = foodAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        binding.buttonMinusFood.setOnClickListener(){
            foodAmount-= priceList.pricelist[3].toDouble()
            amount -= priceList.pricelist[3].toDouble()

            binding.amountFood.text = foodAmount.toString()
            binding.amountBooking.text = amount.toString()
            isCoasternull()

        }

        binding.backArrow.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }


        binding.coasterPic.setOnClickListener(){

            if (amount!=0.0){


                app.bookNewDebt(currentDebt, amount)


                beerAmount=0.0
                longDrinkAmount=0.0
                nonAlcoholicAmount=0.0
                foodAmount=0.0
                amount=0.0

                binding.amountBeer.text="0.0"
                binding.amountLongDrink.text="0.0"
                binding.amountNonalcoholic.text="0.0"
                binding.amountFood.text="0.0"
                binding.amountBooking.text="0.0"


                Toast.makeText(this, "Buchung erfolgreich!", Toast.LENGTH_SHORT).show()

            }else{

                Toast.makeText(this, "Nichts zu buchen!", Toast.LENGTH_SHORT).show()
            }



        }


    }

    fun isCoasternull(){

        if (amount>0.0){

            binding.coasterPic.setImageResource(R.mipmap.bierdeckel_striche)

        }else{

            binding.coasterPic.setImageResource(R.drawable.bierdeckel)
        }

    }




}