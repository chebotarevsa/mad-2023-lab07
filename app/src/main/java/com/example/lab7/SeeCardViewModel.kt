package com.example.lab7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class SeeCardViewModel(private val database: CardDatabase) : ViewModel() {
    private var _card = MediatorLiveData<Card>()
    val card: LiveData<Card> = _card

    fun setCardOfFragment(cardId: Int) {
        _card.addSource(database.cardDao().findById(cardId)) {
            _card.value = it
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
                return SeeCardViewModel(CardDatabase.getInstance(application)) as T
            }
        }
    }
}
