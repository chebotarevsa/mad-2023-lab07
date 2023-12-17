package com.example.lab7mobile

import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab7mobile.databinding.FragmentAddCardBinding


class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private val viewModel: AddCardViewModel by viewModels { AddCardViewModel.Factory(index) }

    private val args by navArgs<AddCardFragmentArgs>()
    private val index by lazy { args.id }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCardBinding.inflate(inflater, container, false)

        if (index != NEW_CARD) {
            val  card = viewModel.card.value!!
            binding.editTextText.setText(card.question)
            binding.editTextText2.setText(card.example)
            binding.editTextText3.setText(card.answer)
            binding.editTextText4.setText(card.translate)
            if (card.image != null) {
                viewModel.setImageBitmap(card.image)
            } else {
                setupDefaultImage()
            }
        }

        binding.imageView2.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.button.setOnClickListener {
            addTermCard()
        }

        viewModel.imageBitmap.observe(viewLifecycleOwner) { bitmap ->
            binding.imageView2.setImageBitmap(bitmap)
        }

        return binding.root
    }

    private val getImage = registerForActivityResult(ImageContract()) { uri ->
        uri?.let {
            val image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(this.requireContext().contentResolver, uri)
            } else {
                TODO("VERSION.SDK_INT < P")
            }
            viewModel.setImageBitmap(ImageDecoder.decodeBitmap(image))
        }
    }

    private fun addTermCard() {
        val question = binding.editTextText.text.toString()
        val hint = binding.editTextText2.text.toString()
        val answer = binding.editTextText3.text.toString()
        val translate = binding.editTextText4.text.toString()
        val image = viewModel.imageBitmap.value

        if (question.isEmpty() || hint.isEmpty() || answer.isEmpty() || translate.isEmpty()) {
            Toast.makeText(this.context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addOrUpdateCard(question, hint, answer, translate, image)
        findNavController().popBackStack()
    }

    private fun setupDefaultImage() {
        val imageWidth = getResources().getDimension(R.dimen.WidthimageViewAddCard).toInt()
        val imageHeight = getResources().getDimension(R.dimen.HeightimageViewAddCard).toInt()
        val yourBitmap = getDrawable(requireContext(), R.drawable.ic_image)!!.toBitmap(imageWidth, imageHeight)
        viewModel.setImageBitmap(yourBitmap)
    }
}
