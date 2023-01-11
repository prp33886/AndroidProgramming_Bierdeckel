package org.wit.bierdeckel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.wit.bierdeckel.MainActivity
import org.wit.bierdeckel.R
import org.wit.bierdeckel.databinding.FragmentHomeBinding
import org.wit.bierdeckel.main.MainApp



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var app: MainApp
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var uID: String

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        app = (activity?.application as MainApp)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        app.getUserDB(firebaseAuth.currentUser!!.uid)

        binding.kalendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth-${month+1}-$year"
            binding.textView6.text = date + ": Keine Veranstaltungen geplant!"
        }


        binding.bierdeckelView.setOnClickListener(){
            deckelAktualisieren()
        }





    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun deckelAktualisieren(){

        val currentDebt = app.getUserDebt(app.user.userID)

        when(currentDebt.schulden!!){

            0.0 ->         binding.bierdeckelView.setImageResource(R.drawable.bierdeckel_kreis)
            in 0.1..1.4 -> binding.bierdeckelView.setImageResource(R.drawable.eins)
            in 1.5..2.4 -> binding.bierdeckelView.setImageResource(R.drawable.zwei)
            in 2.5..3.4 -> binding.bierdeckelView.setImageResource(R.drawable.drei)
            in 3.5..4.4 -> binding.bierdeckelView.setImageResource(R.drawable.vier)
            in 4.5..5.4 -> binding.bierdeckelView.setImageResource(R.drawable.fuenf)
            in 5.5..6.4 -> binding.bierdeckelView.setImageResource(R.drawable.sechs)
            in 6.5..7.4 -> binding.bierdeckelView.setImageResource(R.drawable.sieben)
            in 7.5..8.4 -> binding.bierdeckelView.setImageResource(R.drawable.acht)
            in 8.5..9.4 -> binding.bierdeckelView.setImageResource(R.drawable.neun)
            in 9.5..10.4 -> binding.bierdeckelView.setImageResource(R.drawable.zehn)
            else -> binding.bierdeckelView.setImageResource(R.drawable.zahltag)

        }


    }



}