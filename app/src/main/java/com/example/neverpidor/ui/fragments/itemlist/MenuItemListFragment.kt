package com.example.neverpidor.ui.fragments.itemlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.neverpidor.databinding.FragmentMenuItemListBinding
import com.example.neverpidor.ui.fragments.BaseFragment

class MenuItemListFragment: BaseFragment() {

    private var _binding: FragmentMenuItemListBinding? = null
    private val binding: FragmentMenuItemListBinding
    get() = _binding!!
    private val args: MenuItemListFragmentArgs by navArgs()
    val viewModel: MenuItemListViewModel by viewModels()

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

        val controller = MenuItemListEpoxyController(args.itemId)
        viewModel.getSnacks()
        viewModel.snacks.observe(viewLifecycleOwner) {
            controller.snacks = it
        }

        Log.e("MenuFragment", args.itemId.toString())
        binding.itemListRv.setControllerAndBuildModels(controller)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}