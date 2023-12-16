package com.example.lab7.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab7.db.entity.Card
import com.example.lab7.service.CardService
import com.example.lab7.util.Failed
import com.example.lab7.util.Status
import com.example.lab7.util.Success

class CardManagerViewModel : ViewModel() {

    private var _card = MutableLiveData<Card>()
    val card: LiveData<Card> = _card
    private var _image = MutableLiveData<Bitmap?>()
    val image: LiveData<Bitmap?> = _image

    private var _questionError = MutableLiveData<String>()
    private var _exampleError = MutableLiveData<String>()
    private var _answerError = MutableLiveData<String>()
    private var _translationError = MutableLiveData<String>()

    private var _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    fun setCard(cardId: String) {
        if (cardId != "-1") {
            _card.value = CardService.getCardById(cardId)
        }
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        with(CardService) {
            createNewCard(
                question, example, answer, translation, image
            ).also {
                _card.value = it
                addCard(it)
            }
        }
        _status.value = Success()
    }

    fun updateCardById(
        cardId: String,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        with(CardService) {
            updateCard(
                card.value!!, question, example, answer, translation, image.value
            ).also { _card.value = it }
            updateCardList(cardId, card.value!!)
        }
        _status.value = Success()
    }

    fun setImage(image: Bitmap?) {
        _image.value = image
    }

    fun validateQuestion(question: String) {
        if (question.isBlank()) {
            _questionError.value = "Error"
        }
    }

    fun validateExample(example: String) {
        if (example.isBlank()) {
            _exampleError.value = "Error"
        }
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
}