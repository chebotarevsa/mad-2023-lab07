package com.example.lab7

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class EditCardViewModel : ViewModel() {

    private var _card = MutableLiveData<Card>()
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

    private var database: CardDatabase? = null

    fun setCardOfFragment(cardId: Int) {
        if (cardId != -1) {
            _card.value = database!!.cardDao().findById(cardId)
        }
    }

    fun initDatabase(database: CardDatabase) {
        this.database = database
    }

    fun updateCardById(
        cardId: Int,
        question: String,
        example: String,
        answer: String,
        translation: String,
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        val newCard = database!!.cardDao().findById(cardId)
            .copy(
                question = question,
                example = example,
                answer = answer,
                translation = translation,
                image = image.value
            )
        database!!.cardDao().update(newCard)
        _status.value = Success()
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        database!!.cardDao().insert(Card(null, question, example, answer, translation, image))
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