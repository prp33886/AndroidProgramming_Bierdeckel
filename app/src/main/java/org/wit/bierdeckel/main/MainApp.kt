package org.wit.bierdeckel.main

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import org.wit.bierdeckel.models.debtModel
import org.wit.bierdeckel.models.priceListModel
import org.wit.bierdeckel.models.userModel
import java.io.File
import android.R


class MainApp : Application() {

    var schulden : ArrayList<debtModel> = ArrayList<debtModel>()
    lateinit var user : userModel
    lateinit var database: DatabaseReference
    var pricelist : priceListModel = priceListModel("default", ArrayList<String>())
    var markerList = mutableListOf<Marker>()
    lateinit var loadedProfilpic : Bitmap


    override fun onCreate() {
        super.onCreate()
        pricelist.setDefault()
        getCurrentPriceList()

    }


    // ToDo: DB verbindung herstellen
    private fun getCurrentPriceList() {
        println("Pricelist abrufen von DB")
    }


    fun getUserDB(uID: String){

        var vorName=""
        var nachName=""
        var alter=""
        var geschlecht=""
        var email=""
        var telefonNummer=""
        var userID=""
        var land=""
        var stadt=""
        var schulden = debtModel("","",0.0)


        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User")
        database.child(uID).get().addOnSuccessListener {

            vorName= it.child("vorName").value.toString()
            nachName=it.child("nachName").value.toString()
            alter=it.child("alter").value.toString()
            geschlecht=it.child("geschlecht").value.toString()
            email =it.child("email").value.toString()
            telefonNummer =it.child("telefonNummer").value.toString()
            userID =it.child("userID").value.toString()
            land =it.child("land").value.toString()
            stadt =it.child("stadt").value.toString()

            schulden.schuldnerNachname=vorName
            schulden.schuldnerNachname=nachName
            schulden.schulden= it.child("schulden").child("schulden").value.toString().toDouble()

            this.user = userModel(vorName, nachName, alter, geschlecht,email,telefonNummer, userID, land, stadt, schulden)
        }


        var picName = uID + "_ProfilPic"
        var storage = FirebaseStorage.getInstance().reference.child("Profilbilder/$picName")

        val localFile = File.createTempFile("tempImage", "jpg")
        storage.getFile(localFile).addOnSuccessListener {


            loadedProfilpic = BitmapFactory.decodeFile(localFile.absolutePath)

        }.addOnFailureListener{


        }





    }


    fun getDebts(){

        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        database.child("Schulden").get().addOnCompleteListener {
            if (it.isSuccessful) {
                val gti = object : GenericTypeIndicator<HashMap<String,debtModel>>() {}
                var s = it.result.getValue(gti)


                if (s != null) {
                    schulden.clear()
                    s.forEach { key, value -> schulden.add(value) }

                } else{
                    println("Schuldenliste konnte nicht hinzugefügt werden")
                }


            } else {
                println("Fehler beim Schuldenabruf")
            }
        }

    }


    fun getUserDebt(uID : String) : debtModel{

        for (each in schulden){
            if (each.schuldnerID==uID) {
                return each
            }
        }
        return debtModel("","",0.0,uID)
    }

    fun updateUserDebt(debt : debtModel){
        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference()


        database.child("Schulden").child(debt.schuldnerID.toString()).setValue(debt)

    }

    fun getAppUser() : userModel{
        var firebaseAuth = FirebaseAuth.getInstance()

        if(this.user==null){
            getUserDB(firebaseAuth.currentUser!!.uid)
            return this.user
        }else{
            return this.user
        }


    }


    //dummyschulden einfügen


    fun bookNewDebt(currentDebt: debtModel, amount: Double) {

        var currentUID = currentDebt.schuldnerID.toString()

        getUserDebt(currentUID).schulden = getUserDebt(currentUID).schulden?.plus(amount)

        updateUserDebt(getUserDebt(currentUID))

    }


    fun dummyBars(){
        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Bars")

        var barNames = ArrayList<String>()
        var i =0


        while (i<4){
            var name = "Partyroom ${i}"
            barNames.add(name)

            i++
        }

        database.setValue(barNames)

    }
    fun dummySchulden(){
        database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Schulden")
        var i =0
        while (i<10){
            var x= debtModel("Pascal$i", "Pribyl", 10.2,i.toString())
            schulden.add(x.copy())

            database.child("$i").setValue(x)

            i++
        }


    }

}