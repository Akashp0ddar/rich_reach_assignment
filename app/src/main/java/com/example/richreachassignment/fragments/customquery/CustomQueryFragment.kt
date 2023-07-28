package com.example.richreachassignment.fragments.customquery

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.richreachassignment.MainViewModel
import com.example.richreachassignment.R
import com.example.richreachassignment.ViewModelFactory
import com.example.richreachassignment.databinding.FragmentCustomQueryBinding
import com.example.richreachassignment.repository.Repository


class CustomQueryFragment : Fragment(R.layout.fragment_custom_query) {
    private lateinit var binding: FragmentCustomQueryBinding
    private val viewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            viewModelProviderRepository = Repository()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomQueryBinding.bind(view)
        viewModel.initDataBase(context = requireContext())
        onClick()
    }

    private fun onClick() {
        binding.btnSubmitQuery.setOnClickListener {
            if (viewModel.getDetailsListCustomQuery(binding.etQueryInput.text.toString()) != null) {
                viewModel.query = binding.etQueryInput.text.toString()
                viewModel.isQueryNeeded = true
                findNavController().navigate(R.id.action_customQueryFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid Query", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}