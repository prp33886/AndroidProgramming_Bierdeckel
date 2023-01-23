package org.wit.bierdeckel.ui.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.wit.bierdeckel.models.playerModel

class BeerPongDatabase private constructor() {
    companion object {
        @Volatile
        private var instance: BeerPongDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: BeerPongDatabase().also { instance = it }
            }
    }
    private val database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/")
    private val playersRef = database.getReference("players")

    fun getAllPlayers(): LiveData<List<playerModel>> {
        val playersLiveData = MutableLiveData<List<playerModel>>()

        playersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playersList = mutableListOf<playerModel>()
                dataSnapshot.children.forEach {
                    val player = it.getValue(playerModel::class.java)
                    println(player)
                    player?.let { playersList.add(it) }
                }
                playersLiveData.value = playersList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BeerPongDatabase", "Error reading players from Firebase Realtime Database", error.toException())
            }
        })


        return playersLiveData
    }
}

