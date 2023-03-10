package org.wit.bierdeckel.ui.userInformation

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.wit.bierdeckel.databinding.FragmentUserInformationsBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.placemark.showImagePicker
import com.squareup.picasso.Picasso
import org.wit.bierdeckel.helpers.RoundedTransformation
import java.io.File
import java.io.FileOutputStream



class UserInformationsFragment : Fragment() {

    private var _binding: FragmentUserInformationsBinding? = null
    lateinit var app : MainApp
    private val binding get() = _binding!!
    lateinit var database: DatabaseReference
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var bildURI : Uri

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


        binding.buttonUserinformationSpeichern.setOnClickListener(){
            editUserInformations()
            Toast.makeText(context, "Daten wurden gespeichert!", Toast.LENGTH_LONG)

        }
        binding.buttonUserinformationAbbrechen.setOnClickListener(){
            fillUserInfo()
        }

        var c = context
        binding.userProfilPic.setOnClickListener(){

            showImagePicker(imageIntentLauncher, c!!)

        }
        registerImagePickerCallback()


    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {

                            val image = result.data!!.data!!
                            requireContext().contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)

                            bildURI = image

                            Picasso.get()
                                .load(bildURI)
                                .transform(RoundedTransformation())
                                .into(binding.userProfilPic)

                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun editUserInformations(){

        val context = requireContext()
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("??nderung wird verarbeitet!")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val bildName = app.user.userID + "_ProfilPic"


        val storage = FirebaseStorage.getInstance().getReference("Profilbilder/$bildName")
        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()


        var vorname = binding.vorNameTextEingabe.text.toString()
        var nachname= binding.nachNameTextEingabe.text.toString()

        app.user.vorName=vorname
        app.user.nachName=nachname
        app.user.alter=binding.alterTextEingabe.text.toString()

        if(binding.checkBoxMNnlich.isChecked){
            app.user.geschlecht ="M??nnlich"
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


        storage.putFile(bildURI).addOnSuccessListener {


            Toast.makeText(context, "Erfolgreich ge??ndert!", Toast.LENGTH_LONG)
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }.addOnFailureListener(){
            Toast.makeText(context, "Upload Profilbild fehlgeschlagen!", Toast.LENGTH_LONG)
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }



    }

    fun fillUserInfo(){

        binding.vorNameTextEingabe.setText(app.user.vorName)
        binding.nachNameTextEingabe.setText(app.user.nachName)
        binding.alterTextEingabe.setText(app.user.alter)

        if (app.user.geschlecht=="M??nnlich"){
            binding.checkBoxMNnlich.isChecked = true
        }
        if (app.user.geschlecht=="Weiblich"){
            binding.checkBoxWeiblich.isChecked = true
        }

        binding.eMailTextEingabe.setText(app.user.email)
        binding.telefonNummerTextEingabe.setText(app.user.telefonNummer)
        binding.StadtTextEingabe.setText(app.user.stadt)
        binding.landTextEingabe.setText(app.user.land)

        if(app.loadedProfilpic!=null){

            // Ben??tigt dateierstellung um URI zu erhalten .. Format sonst nicht richtig rund
            val imageFile = File(app.applicationContext.cacheDir, "profilbild.png")

            val os = FileOutputStream(imageFile)

            app.loadedProfilpic.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.close()
            val imageUri = Uri.fromFile(imageFile)


            Picasso.get()
                .load(imageUri)
                .transform(RoundedTransformation())
                .into(binding.userProfilPic)

        }

    }


}