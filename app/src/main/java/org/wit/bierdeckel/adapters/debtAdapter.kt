package org.wit.bierdeckel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.bierdeckel.databinding.CardDebtBinding
import org.wit.bierdeckel.models.debtModel

interface DebtListener {
    fun onDebtClick(debt: debtModel)
}


class debtAdapter constructor(private var debts: List<debtModel>,
                                   private val listener: DebtListener) :
    RecyclerView.Adapter<debtAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDebtBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val debt = debts[holder.adapterPosition]
        holder.bind(debt, listener)
    }

    override fun getItemCount(): Int = debts.size

    class MainHolder(private val binding : CardDebtBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(debt: debtModel, listener: DebtListener) {
            binding.firstName.text = debt.schuldnerVorName
            binding.lastName.text = debt.schuldnerNachname
            binding.debts.text = debt.schulden.toString()
            binding.root.setOnClickListener { listener.onDebtClick(debt) }
        }
    }
}