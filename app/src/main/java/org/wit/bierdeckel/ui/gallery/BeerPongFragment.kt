package org.wit.bierdeckel.ui.gallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.wit.bierdeckel.databinding.FragmentBeerPongBinding

class BeerPongFragment : Fragment() {
    private lateinit var viewModel: BeerPongViewModel

    private var _binding: FragmentBeerPongBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeerPongBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(BeerPongViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.topPlayers.observe(viewLifecycleOwner, Observer { players ->
            binding.firstTeam.text = players[0].name
            binding.secondTeam.text = players[1].name
            binding.thirdTeam.text = players[2].name

            binding.firstTeamPoints.text= players[0].wins.toString() + " Punkte"
            binding.secondTeamPoints.text= players[1].wins.toString() + " Punkte"
            binding.thirdTeamPoints.text= players[2].wins.toString() + " Punkte"
        })
    }
}
