package org.wit.bierdeckel.ui.userInformation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.R
import org.wit.bierdeckel.databinding.FragmentUserInformationsBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.userModel

class UserInformationsFragment : Fragment() {

    private var _binding: FragmentUserInformationsBinding? = null
    lateinit var app : MainApp
    private val binding get() = _binding!!
    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View {
        _binding = FragmentUserInformationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        app = (activity?.application as MainApp)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Userinformationen setzten
            fillUserInfo()

       // binding.userProfilPic.setImageDrawable(app.user.profilPic)

        binding.buttonUserinformationSpeichern.setOnClickListener(){
            editUserInformations()
            Toast.makeText(context, "Daten wurden gespeichert!", Toast.LENGTH_LONG)

        }
        binding.buttonUserinformationAbbrechen.setOnClickListener(){
            fillUserInfo()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun editUserInformations(){



        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        var vorname = binding.vorNameTextEingabe.text.toString()
        var nachname= binding.nachNameTextEingabe.text.toString()

        app.user.vorName=vorname
        app.user.nachName=nachname
        app.user.alter=binding.alterTextEingabe.text.toString()

        if(binding.checkBoxMNnlich.isChecked){
            app.user.geschlecht ="Männlich"
        }else if (binding.checkBoxWeiblich.isChecked){
            app.user.geschlecht="Weiblich"
        }else{
            app.user.geschlecht="Divers"
        }

        app.user.email=binding.eMailTextEingabe.text.toString()
        app.user.telefonNummer=binding.telefonNummerTextEingabe.text.toString()
        app.user.stadt=binding.StadtTextEingabe.text.toString()
        app.user.land=binding.landTextEingabe.text.toString()


        //Schuldenliste DB aktualisieren
        var newDebt = app.getUserDebt(app.user.userID)
        newDebt.schuldnerVorName=vorname
        newDebt.schuldnerNachname=nachname
        newDebt.schuldnerID=app.user.userID
        newDebt.schulden=app.user.schulden.schulden


        database.child("Schulden").child(app.user.userID).setValue(newDebt)
        database.child("User").child(app.user.userID).setValue(app.user)

    }

    fun fillUserInfo(){

        binding.vorNameTextEingabe.setText(app.user.vorName)
        binding.nachNameTextEingabe.setText(app.user.nachName)
        binding.alterTextEingabe.setText(app.user.alter)

        if (app.user.geschlecht=="Männlich"){
            binding.checkBoxMNnlich.isChecked = true
        }
        if (app.user.geschlecht=="Weiblich"){
            binding.checkBoxWeiblich.isChecked = true
        }

        binding.eMailTextEingabe.setText(app.user.email)
        binding.telefonNummerTextEingabe.setText(app.user.telefonNummer)
        binding.StadtTextEingabe.setText(app.user.stadt)
        binding.landTextEingabe.setText(app.user.land)

    }
}