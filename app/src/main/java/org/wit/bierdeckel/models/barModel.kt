package org.wit.bierdeckel.models

import org.wit.bierdeckel.enums.kategorien

data class barModel(var name : String, var beschreibung : String , var kategorie : kategorien){



    fun defaultWerte() {
        this.name= ""
        this.beschreibung = ""
        this.kategorie = kategorien.Default
    }

}
