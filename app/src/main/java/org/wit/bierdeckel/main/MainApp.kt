package org.wit.bierdeckel.main

import android.app.Application
import android.provider.Settings
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.SnapshotHolder
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import org.wit.bierdeckel.R
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.userModel

class MainApp : Application() {

    var schulden : ArrayList<debtModel> = ArrayList<debtModel>()
    lateinit var user : userModel
    lateinit var database: DatabaseReference



    override fun onCreate() {
        super.onCreate()

        dummySchulden()

        val androidId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference(androidId)
        getUserDB(androidId)


    }


    fun getUserDB(androidId: String){

        var vorName=""
        var nachName=""
        var email=""
        var telefonNummer=""
        var userID =""
        var geburtsDatum =""
        var plz =""
        var stadt =""
        var strasse =""

        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User")
        database.child(androidId).get().addOnSuccessListener {

            vorName= it.child("vorName").value.toString()
            nachName=it.child("nachName").value.toString()
            email=it.child("email").value.toString()
            telefonNummer=it.child("telefonNummer").value.toString()
            userID =it.child("userID").value.toString()
            geburtsDatum =it.child("geburtsDatum").value.toString()
            plz =it.child("plz").value.toString()
            stadt =it.child("stadt").value.toString()
            strasse =it.child("strasse").value.toString()
            this.user = userModel(vorName, nachName, email, telefonNummer, userID, geburtsDatum, plz, stadt, strasse)
        }



    }

    //dummyschulden einfügen
    // ToDo: durch DB verbindung ersetzen
    fun dummySchulden(){

        var i =0
        while (i<10){
            var x= debtModel("Pascal$i", "Pribyl", 10.2)
            schulden.add(x.copy())

            i++
        }

    }

    //dummyUser einfügen
    // ToDo: durch DB verbindung ersetzen
    fun dummyUser(){
        var vorName="Hans"
        var nachName="Huber"
        var email="Hans-Huber@gmail.com"
        var telefonNummer="016098790600"
        var userID ="12345"
        var geburtsDatum ="01.01.1998"
        var plz ="84347"
        var stadt ="Triftern"
        var strasse ="Am Dornlandl 13"
        var profilPic = R.drawable.signin_screen.toDrawable()

        this.user = userModel(vorName, nachName, email, telefonNummer, userID, geburtsDatum, plz, stadt, strasse)
        //this.user.profilPic=profilPic
    }

}