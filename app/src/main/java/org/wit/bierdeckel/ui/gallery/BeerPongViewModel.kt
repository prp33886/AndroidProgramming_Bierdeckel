package org.wit.bierdeckel.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.wit.bierdeckel.models.playerModel

class BeerPongViewModel : ViewModel() {

    private val repository = BeerPongRepository()
    val topPlayers: LiveData<List<playerModel>> = getPlayers()


    private fun getPlayers(): LiveData<List<playerModel>> {
        return Transformations.map(repository.getAllPlayers()) { players ->
            players.sortedByDescending { it.wins }.take(3)
        }
    }


}

