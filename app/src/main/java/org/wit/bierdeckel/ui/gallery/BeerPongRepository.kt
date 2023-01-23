package org.wit.bierdeckel.ui.gallery

import androidx.lifecycle.LiveData
import org.wit.bierdeckel.models.playerModel

class BeerPongRepository {

    private val database = BeerPongDatabase.getInstance()

    fun getAllPlayers(): LiveData<List<playerModel>> {
        return database.getAllPlayers()
    }

}
