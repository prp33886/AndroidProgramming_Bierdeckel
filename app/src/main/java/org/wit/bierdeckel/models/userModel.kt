package org.wit.bierdeckel.models

import android.graphics.drawable.Drawable

data class userModel(var vorName: String ="",
                     var nachName: String ="",
                     var email: String="",
                     var telefonNummer: String="",
                     var userID: String,
                     var geburtsDatum: String,
                     var plz: String,
                     var strasse: String,
                     var stadt: String,

                     ) {
    //lateinit var profilPic: Drawable

    fun updateUser (vorName: String, nachName: String, email: String, telefonNummer: String, userID: String, geburtsDatum: String, plz: String, stadt: String, strasse: String){

        this.vorName = vorName
        this.nachName = nachName
        this.email = email
        this.telefonNummer = telefonNummer
        this.userID = userID
        this.geburtsDatum = geburtsDatum
        this.plz = plz
        this.stadt = stadt
        this.strasse = strasse

    }

}
