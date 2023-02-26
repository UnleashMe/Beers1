package com.example.neverpidor.ui.fragments.itemlist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.neverpidor.R
import com.example.neverpidor.databinding.AddBeerDialogBinding
import com.example.neverpidor.databinding.FragmentMenuItemListBinding
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.ui.fragments.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuItemListFragment: BaseFragment() {

    private var _binding: FragmentMenuItemListBinding? = null
    private val binding: FragmentMenuItemListBinding
    get() = _binding!!
    private val args: MenuItemListFragmentArgs by navArgs()
    private val viewModel: MenuItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = MenuItemListEpoxyController(args.itemId) {
            showAddBeerDialog(it)
        }
        controller.isLoading = true
        when (args.itemId ) {
            0 -> {
                viewModel.getBeers()
                viewModel.beers.observe(viewLifecycleOwner) {
                    controller.beerList = it
                }
            }
            1 -> {
                viewModel.getSnacks()
                viewModel.snacks.observe(viewLifecycleOwner) {
                    controller.snacks = it
                }
            }
        }
        viewModel.beerResponse.observe(viewLifecycleOwner) {
            it.enqueue(object : Callback<BeerRequest> {
                override fun onResponse(call: Call<BeerRequest>, response: Response<BeerRequest>) {
                    Toast.makeText(requireContext(), "Пиво добавлено", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<BeerRequest>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message ?: "Failed", Toast.LENGTH_LONG).show()
                    Log.e("FAIL", t.message ?: "fuck")
                }
            })
        }
        binding.itemListRv.setControllerAndBuildModels(controller)
        binding.itemListRv.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAddBeerDialog(type: String) {
        val binding = AddBeerDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить пефко")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена") {dialog, pos ->
                dialog.dismiss()
            }.create()
        dialog.setOnShowListener {
            binding.etName.requestFocus()
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val name = binding.etName.text.toString()
                if (name.isBlank()) {
                    binding.etName.error = "Имя не может быть пустым"
                    return@setOnClickListener
                }
                val description = binding.etDescription.text.toString()
                if (description.isBlank()) {
                    binding.etDescription.error = "Придумай хоть что-нибудь"
                    return@setOnClickListener
                }
                val price = binding.etPrice.text.toString().toDouble()
                if (price < 50.0) {
                    binding.etPrice.error = "Хули тут так мало?"
                    return@setOnClickListener
                }
                if (price > 500.0) {
                    binding.etPrice.error = "Чет дорого"
                    return@setOnClickListener
                }
                val alcPercentage = binding.etAlc.text.toString().toDouble()
                Log.e("ALC", alcPercentage.toString())
                if (alcPercentage > 20.0) {
                    binding.etAlc.error = "У нас здест не кабак"
                    return@setOnClickListener
                }
                val volume = binding.etVolume.text.toString().toDouble()
                if (volume < 0.25) {
                    binding.etVolume.error = "Такими темпами не напьешься"
                    return@setOnClickListener
                }
                if (volume > 3.00) {
                    binding.etVolume.error = "Бочками пиво не продаем"
                }
                val beerRequest = BeerRequest(alcPercentage, description, name, price, type, volume)
                viewModel.addBeer(beerRequest)
                dialog.dismiss()
            }

        }
        dialog.show()
    }
}