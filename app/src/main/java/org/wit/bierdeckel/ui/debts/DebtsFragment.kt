package org.wit.bierdeckel.ui.debts


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.wit.bierdeckel.R
import org.wit.bierdeckel.adapters.DebtListener
import org.wit.bierdeckel.adapters.debtAdapter
import org.wit.bierdeckel.databinding.FragmentDebtsBinding
import org.wit.bierdeckel.main.MainApp
import org.wit.bierdeckel.models.debtModel



class DebtsFragment : Fragment(), DebtListener {

    private var _binding: FragmentDebtsBinding? = null
    private lateinit var adapter: debtAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var app : MainApp


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

        app = (activity?.application as MainApp)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.schuldenHinzufuegen.setOnClickListener { view ->
            Snackbar.make(view, "DummyDaten einfügen", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = debtAdapter(app.schulden, this)
        recyclerView.adapter= adapter


        binding.switch1.setOnClickListener(){
            Snackbar.make(view, "Hier liste filtern", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDebtClick(debt: debtModel) {

        val intent = Intent(context , BookDebtsActivity::class.java)
        intent.putExtra("bierdeckel", debt)
        startActivity(intent)

    }

}