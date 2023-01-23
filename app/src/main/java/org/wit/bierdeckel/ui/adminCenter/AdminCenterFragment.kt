package org.wit.bierdeckel.ui.adminCenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import org.wit.bierdeckel.R
import org.wit.bierdeckel.databinding.FragmentAdminCenterBinding
import org.wit.bierdeckel.databinding.FragmentHomeBinding
import org.wit.bierdeckel.enums.kategorien
import org.wit.bierdeckel.helpers.RoundedTransformation
import org.wit.bierdeckel.models.barModel
import org.wit.placemark.showImagePicker


class AdminCenterFragment : Fragment() {


    private var _binding: FragmentAdminCenterBinding? = null
    private val binding get() = _binding!!
    lateinit var database: DatabaseReference
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var bildURI : Uri

    private val bar : barModel = barModel("", "", kategorie = kategorien.Default)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAdminCenterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.buttonAnlegen.setOnClickListener(){

            bar.name = binding.barNameText.text.toString()
            bar.beschreibung = binding.beschreibungText.text.toString()
            var kat = binding.kategorieText.text.toString().uppercase()

            if (kat == "PRIVATEBAR"){

                bar.kategorie= kategorien.PrivateBar

            }else if(kat == "HUETTE"){

                bar.kategorie=kategorien.Huette

            }else if (kat == "PARTYRAUM"){

                bar.kategorie=kategorien.Partyraum

            }else {
                bar.kategorie= kategorien.Default

            }

            if (bar.name.isNotEmpty() && bar.beschreibung.isNotEmpty() && kat.isNotEmpty()){

                database = FirebaseDatabase.getInstance("https://prp33886-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Bars")
                database.push().setValue(bar.name)


                Toast.makeText(requireContext().applicationContext, "Deine Bar wurde angelegt!", Toast.LENGTH_SHORT).show()

            }else{

                Toast.makeText(requireContext().applicationContext, "Keine Leeren Felder!", Toast.LENGTH_SHORT).show()
            }



        }


        var c = context

        binding.barView.setOnClickListener(){

            showImagePicker(imageIntentLauncher, c!!)

        }

        registerImagePickerCallback()


    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {

                            val image = result.data!!.data!!
                            requireContext().contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)

                            bildURI = image

                            Picasso.get()
                                .load(bildURI)
                                .transform(RoundedTransformation())
                                .into(binding.barView)

                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}