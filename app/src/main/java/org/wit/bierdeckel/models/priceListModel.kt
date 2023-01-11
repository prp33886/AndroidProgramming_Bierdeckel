package org.wit.bierdeckel.models


// Pricelist: 0 Beer 1 Longdrink 2 Nonalcoholic 3 Food
data class priceListModel(val partyRoomID :String , var pricelist : List<String>){


    fun setDefault(){

        var default :ArrayList<String> = ArrayList<String>()
        default.add(0, "1.5")
        default.add(1, "3.0")
        default.add(2, "1.0")
        default.add(3, "2.5")

        this.pricelist = default
    }


}


