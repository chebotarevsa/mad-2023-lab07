package com.example.lab7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListCardViewModel : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    private var database: CardDatabase? = null
    fun initDatabase(database: CardDatabase) {
        this.database = database
        _cards.value = database.cardDao().findAll()

    }

    fun setCardOfFragment(cardId: Int) {
        _card.value = database!!.cardDao().findById(cardId)
    }

    fun removeCardById(cardId: Int) {
        with(database!!.cardDao()) {
            delete(findById(cardId))
            _cards.value = findAll()
        }
    }

    fun getCardShortData(): String {
        return "\n ${card.value!!.answer} / ${card.value!!.translation}"
    }
}