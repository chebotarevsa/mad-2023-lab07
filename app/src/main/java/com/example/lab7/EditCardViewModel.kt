package com.example.lab7

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch


class EditCardViewModel(private val database: CardDatabase, val cardId: Int) : ViewModel() {
    private var _currentCard: Card? = null;
    private val _dbCard = database.cardDao().findById(cardId);
    private val _card = MediatorLiveData<Card>()
    val card: LiveData<Card> = _card

    private var _questionError = MutableLiveData<String>()
    val questionError: LiveData<String> = _questionError
    private var _exampleError = MutableLiveData<String>()
    val exampleError: LiveData<String> = _exampleError
    private var _answerError = MutableLiveData<String>()
    val answerError: LiveData<String> = _answerError
    private var _translationError = MutableLiveData<String>()
    val translationError: LiveData<String> = _translationError

    private var _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    init {
        _card.addSource(_dbCard) {
            if (!isNewCard())
                _card.value = it
            else
                _card.value = newCard
        }
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }

    fun validateQuestion(newQuestion: String, oldQuestion: String) {
        if (newQuestion.isBlank() && oldQuestion.isNotBlank()) {
            _questionError.value = "Error"
        }
    }

    fun validateExample(example: String) {
        if (example.isBlank() && (_currentCard?.example ?: "").isNotBlank()) {
            _exampleError.value = "Error"
        }
        if (example != card.value?.example)
            _currentCard = card.value?.let { it.copy(example = example) }

    }

    fun validateAnswer(answer: String) {
        if (answer.isBlank()) {
            _answerError.value = "Error"
        }
    }

    fun validateTranslation(translation: String) {
        if (translation.isBlank()) {
            _translationError.value = "Error"
        }
    }

    fun isNewCard() = cardId == -1;

    fun saveCard(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) {
        val image = image.value
        if (isValid(question, example, answer, translation)) {
            _status.value = Failed("One or several fields are blank")
        } else {
            val newCard = card.value?.copy(
                question = question,
                example = example,
                answer = answer,
                translation = translation,
                image = image
            )
            newCard?.let {
                viewModelScope.launch {
                    if (isNewCard())
                        database.cardDao().insert(it)
                    else
                        database.cardDao().update(it)
                    _status.value = Success()
                }
            }
        }
    }

    private fun isValid(
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) = question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()

    private val newCard = Card(null, "", "", "", "");

    override fun onCleared() {
        _card.removeSource(_dbCard)
        super.onCleared()
    }

    companion object {
        fun Factory(cardId: Int): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return EditCardViewModel(CardDatabase.getInstance(application), cardId) as T
            }
        }
    }
}