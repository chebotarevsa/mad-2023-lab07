package com.example.lab7

import android.graphics.Bitmap
import android.provider.MediaStore.Audio.Media
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.concurrent.thread


class EditCardViewModel(private val database: CardDatabase) : ViewModel() {

    private var _card = MediatorLiveData<Card>()
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

    fun setCardOfFragment(cardId: Int) {
        if (cardId != -1) {
            val card = database.cardDao().findById(cardId)
            _card.addSource(card) {
                _card.value = it
            }
        }
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
        _card.value = _card.value?.copy(
            question = question,
            example = example,
            answer = answer,
            translation = translation,
            image = image.value
        )
        _card.value?.let {
            thread { database.cardDao().update(it) }
        }
        _status.value = Success()
    }

    fun addCard(
        question: String, example: String, answer: String, translation: String, image: Bitmap?
    ) = if (question.isBlank() || example.isBlank() || answer.isBlank() || translation.isBlank()) {
        _status.value = Failed("One or several fields are blank")
    } else {
        thread {
            database.cardDao().insert(Card(null, question, example, answer, translation, image))
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return EditCardViewModel(CardDatabase.getInstance(application)) as T
            }
        }
    }
}