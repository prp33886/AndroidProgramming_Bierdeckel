package org.wit.bierdeckel.models

import android.graphics.drawable.Drawable
import android.net.Uri

data class userModel(var vorName: String ="",
                     var nachName: String ="",
                     var alter: String,
                     var geschlecht:String,
                     var email: String="",
                     var telefonNummer: String="",
                     var userID: String,

                     var land: String,
                     var stadt: String,
                     var schulden: debtModel

                     ) {
    //lateinit var profilPic: Drawable

    fun updateUser (vorName: String, nachName: String, email: String, telefonNummer: String, userID: String, alter: String,land: String, stadt: String){

        this.vorName = vorName
        this.nachName = nachName
        this.email = email
        this.telefonNummer = telefonNummer
        this.userID = userID
        this.alter=alter
        this.land=land
        this.stadt = stadt

    }

}
