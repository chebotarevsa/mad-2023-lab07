package com.example.lab7.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab7.db.entity.Card
import com.example.lab7.service.CardService

class SeeViewModel : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    fun setCard(cardId: String) {
        _card.value = CardService.getCardById(cardId)
    }
}