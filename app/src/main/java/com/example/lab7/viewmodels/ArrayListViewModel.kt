package com.example.lab7.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab7.db.entity.Card
import com.example.lab7.service.CardService

class ArrayListViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    init {
        _cards.value = CardService.cards
    }

    fun setCard(cardId: String) {
        _card.value = CardService.getCardById(cardId)
    }

    fun removeCardById(cardId: String) {
        CardService.removeCard(cardId)
        _cards.value = CardService.cards
    }
}