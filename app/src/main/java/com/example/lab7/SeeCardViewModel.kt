package com.example.lab7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeeCardViewModel : ViewModel() {
    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card

    private var database: CardDatabase? = null
    fun initDatabase(database: CardDatabase) {
        this.database = database
    }

    fun setCardOfFragment(cardId: Int) {
        _card.value = database!!.cardDao().findById(cardId)
    }
}
