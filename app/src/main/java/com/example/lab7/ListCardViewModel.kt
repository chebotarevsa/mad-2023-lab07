package com.example.lab7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.concurrent.thread

class ListCardViewModel(private val database: CardDatabase) : ViewModel() {

    private val _cards = MediatorLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MediatorLiveData<Card>()
    val card: LiveData<Card> = _card

    private var _status = MediatorLiveData<CardFindStatus>()
    val status: LiveData<CardFindStatus> = _status

    init {
        _cards.addSource(database.cardDao().findAll()) {
            _cards.value = it
        }
    }

    fun findCardForDelete(cardId: Int) {
        _status.addSource(database.cardDao().findById(cardId)) {
            _card.value = it
            _status.value = CardFoundSuccess("\n ${it.answer} / ${it.translation}")
        }
    }

    fun removeCardById() {
        with(database.cardDao()) {
            _card.value?.let {
                thread { delete(it) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ListCardViewModel(CardDatabase.getInstance(application)) as T
            }
        }
    }
}