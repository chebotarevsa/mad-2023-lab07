package com.example.lab5

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.FragmentCardListBinding
import androidx.fragment.app.viewModels

class CardListFragment : Fragment() { //Класс фрагмента - родитель
    private var _binding: FragmentCardListBinding? = null //Хранит ссылку на связанную разметку фрагмента
    private val binding get() = _binding!! //При обращении к binding автоматически вызовется _binding
    private lateinit var adapter: AdapterRecyclerView //Адаптер (lateinit - отложенная инициализация)
    private val view_model: CardListViewModel by viewModels() //Лист карточек, список, с мпользованием функции viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(layoutInflater) //Раздуваем
        val recyclerView: RecyclerView = binding.recyclerId
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterRecyclerView(action).apply {
            view_model.list_cards.observe(viewLifecycleOwner){ //Обновление карточки
                cards = it
            }
        }
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val action =
                CardListFragmentDirections.actionCardListFragmentToCardEditFragment(-1)
            findNavController().navigate(action)
        }
        return binding.root

    }
    override fun onDestroy() { //Освобождение памяти
        super.onDestroy()
        _binding = null
    }

    private val action = object : ActionInterface {
        override fun onItemClick(cardId: Int) {
            val action = CardListFragmentDirections.actionCardListFragmentToCardSeeFragment(cardId)
            findNavController().navigate(action)
        }

        override fun onDeleteCard(cardId: Int) {
            val card = Model.getCardById(cardId)
            AlertDialog.Builder(requireContext()).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    Model.removeCard(card.id)
                    adapter.cards = Model.cards
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        requireContext(), "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }
}
