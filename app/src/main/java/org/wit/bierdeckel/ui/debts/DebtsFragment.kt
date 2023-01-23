package org.wit.bierdeckel.ui.debts


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.wit.bierdeckel.R
import org.wit.bierdeckel.adapters.DebtListener
import org.wit.bierdeckel.adapters.debtAdapter
import org.wit.bierdeckel.databinding.FragmentDebtsBinding
import org.wit.bierdeckel.helpers.SwipeToDeleteCallback
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


        binding.schuldenHinzufuegen.setOnClickListener(){

            val intent = Intent(context , AddNewDebtActivity::class.java)
            startActivity(intent)

        }



        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = debtAdapter(app.schulden, this)
        recyclerView.adapter= adapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                app.schulden.removeAt(position)
                recyclerView.adapter?.notifyItemRemoved(position)

            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        binding.switch1.textOn = "Nicht Registrierte"
        binding.switch1.textOff = "Registrierte"

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->

            var filterList = ArrayList<debtModel>()

            if(isChecked){

                binding.switch1.text = binding.switch1.textOn
                Snackbar.make(view, "Nicht Registrierte User", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

                filterList = filterDebtList()


            }else{

                binding.switch1.text = binding.switch1.textOff
                Snackbar.make(view, "Registrierte User", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                filterList=app.schulden
                println(filterList)
                println(app.schulden)

            }

            adapter = debtAdapter(filterList, this)
            recyclerView.adapter=adapter

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


    //ToDo: Filterfunktion bauen
    private fun filterDebtList(): ArrayList<debtModel> {

        var currentList = ArrayList<debtModel>()
        currentList.add(debtModel("Gast", "Eins",0.7))

        return currentList
    }
}