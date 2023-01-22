package org.wit.bierdeckel.ui.debts


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.wit.bierdeckel.R
import org.wit.bierdeckel.adapters.debtAdapter
import org.wit.bierdeckel.databinding.FragmentDebtsBinding
import org.wit.bierdeckel.models.debtModel

class debtsListFragment : Fragment() {

    private var _binding: FragmentDebtsBinding? = null
    private lateinit var adapter: debtAdapter
    private lateinit var recyclerView: RecyclerView
    val schulden = ArrayList<debtModel>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDebtsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dummyDaten()
        binding.schuldenHinzufuegen.setOnClickListener { view ->
            Snackbar.make(view, "DummyDaten einfügen", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = debtAdapter(schulden)
        recyclerView.adapter= adapter

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //dummyschulden einfügen
    // ToDo: durch DB verbindung ersetzen
    fun dummyDaten(){

        var i: Int=0
        while (i<10){
            var x: debtModel = debtModel("Pascal$i", "Pribyl", 10.2)
            schulden.add(x.copy())

            i++
        }

    }

}