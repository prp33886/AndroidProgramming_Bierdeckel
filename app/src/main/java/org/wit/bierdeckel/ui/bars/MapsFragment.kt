package org.wit.bierdeckel.ui.bars

import android.app.AlertDialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.bierdeckel.R
import org.wit.bierdeckel.main.MainApp


class MapsFragment : Fragment() {
    private lateinit var map: GoogleMap

    private lateinit var app: MainApp
    private var markerList = mutableListOf<Marker>()

        private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        this.markerList = app.markerList

        //Markerliste abrufen und setzen
        for (marker in markerList) {
            val markerOptions = MarkerOptions()
                .position(marker.position)
                .title(marker.title)
                .snippet(marker.snippet)
            map.addMarker(markerOptions)
        }

            //Ausgangspunkt
        val triftern = LatLng(48.387602, 12.9619809)
        map.addMarker(MarkerOptions().position(triftern).title("Partyroom: Ritzinger Hüttn"))
        map.moveCamera(CameraUpdateFactory.newLatLng(triftern))


        map.setOnMapLongClickListener { latLng ->

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Erstelle deine Bar!")
            val input = EditText(requireContext())
            builder.setView(input)
            builder.setPositiveButton("Speichern") { dialog, which ->
                val markerName = input.text.toString()

                val marker = map.addMarker(MarkerOptions().position(latLng).title(markerName))
                if (marker != null) {
                    markerList.add(marker)  // Marker zur Liste hinzufügen
                }
            }
            builder.setNegativeButton("Abbrechen") { dialog, which ->
                dialog.cancel()
            }
            builder.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        app = (activity?.application as MainApp)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}

