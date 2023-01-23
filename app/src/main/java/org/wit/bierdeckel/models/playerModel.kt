package org.wit.bierdeckel.models

import com.google.firebase.database.PropertyName

data class playerModel(
    @JvmField
    @PropertyName("name")
    val name: String,
    @JvmField
    @PropertyName("wins")
    val wins: Int){

    constructor(): this("", 0)

}

