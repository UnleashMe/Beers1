package com.example.neverpidor.ui.fragments.addbeer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.neverpidor.databinding.AddBeerFragmentBinding
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackRequest
import com.example.neverpidor.ui.fragments.BaseFragment

class AddBeerFragment: BaseFragment() {

    private var _binding: AddBeerFragmentBinding? = null
    private val binding: AddBeerFragmentBinding
    get() = _binding!!

    private var updateMode = false
    private val viewModel: AddBeerViewModel by viewModels()

    private val args: AddBeerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddBeerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id == 1) {
            binding.volumeTextLayout.isGone = true
            binding.alcTextLayout.isGone = true
            binding.saveButton.setOnClickListener {
                validateAndSave(args.id)
            }
            observeSnackResponse()

        } else {
            binding.saveButton.setOnClickListener {
                validateAndSave(args.id)
            }
            observeBeerResponse()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateAndSave(id: Int) {
        val name = binding.nameEditText.text.toString()
        if (name.isBlank()) {
            binding.nameLayout.error = "Имя не может быть пустым"
            return
        }
        val description = binding.descriptionEt.text.toString()
        if (description.isBlank()) {
            binding.descriptionTextLayout.error = "Придумай хоть что-нибудь"
            return
        }
        val type = binding.typeEt.text.toString()
        if (type.isBlank()) {
            binding.typeTextLayout.error = "Так не пойдет"
            return
        }
        val price = binding.priceEt.text.toString().toDouble()
        if (price < 50.0) {
            binding.priceTextLayout.error = "Хули тут так мало?"
            return
        }
        if (price > 500.0) {
            binding.priceTextLayout.error = "Чет дорого"
            return
        }
        if (id == 0) {
            val alcPercentage = binding.alcEt.text.toString().toDouble()
            Log.e("ALC", alcPercentage.toString())
            if (alcPercentage > 20.0) {
                binding.alcTextLayout.error = "У нас здест не кабак"
                return
            }
            val volume = binding.volumeEt.text.toString().toDouble()
            if (volume < 0.25) {
                binding.volumeTextLayout.error = "Такими темпами не напьешься"
                return
            }
            if (volume > 3.00) {
                binding.volumeTextLayout.error = "Бочками пиво не продаем"
            }
            val beerRequest = BeerRequest(alcPercentage, description, name, price, type, volume)
            viewModel.addBeer(beerRequest)
        } else {
            val request = SnackRequest(description, name, price, type)
            viewModel.addSnack(request)
        }
        navController.navigateUp()
    }

    private fun observeBeerResponse() {
        viewModel.beerResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "${it.body()?.createdBeverage?.name} was successfully added",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), it.errorBody().toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun observeSnackResponse() {
        viewModel.snackResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "${it.body()?.deletedSnack?.name} was successfully added",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), it.errorBody().toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}