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
        binding.profilInfoText.setText("Deine Profilinformationen, ${app.user.vorName}!")
        binding.geburtsDatumTextEingabe.setText(app.user.geburtsDatum)
        binding.telefonNummerTextEingabe.setText(app.user.telefonNummer)
        binding.adressePlzEingabe.setText(app.user.plz)
        binding.adresseStadtEingabe.setText(app.user.stadt)
        binding.adresseStrasseEingabe.setText(app.user.strasse)
        binding.eMailTextEingabe.setText(app.user.email)
       // binding.userProfilPic.setImageDrawable(app.user.profilPic)

        binding.buttonUserinformationSpeicher.setOnClickListener(){
            editUserInformations()
            Toast.makeText(context, "Daten wurden gespeichert!", Toast.LENGTH_LONG)


        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun editUserInformations(){
            database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
            app.user.geburtsDatum = binding.geburtsDatumTextEingabe.text.toString()
            app.user.telefonNummer = binding.telefonNummerTextEingabe.text.toString()
            app.user.plz = binding.adressePlzEingabe.text.toString()
            app.user.stadt = binding.adresseStadtEingabe.text.toString()
            app.user.strasse = binding.adresseStrasseEingabe.text.toString()
            database.child("User").child(app.user.userID).setValue(app.user)

    }
}