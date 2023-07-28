package com.example.richreachassignment.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.richreachassignment.MainViewModel
import com.example.richreachassignment.R
import com.example.richreachassignment.ViewModelFactory
import com.example.richreachassignment.databinding.FragmentListBinding
import com.example.richreachassignment.fragments.list.adapters.ManagersListAdapter
import com.example.richreachassignment.repository.Repository


class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding
    private val viewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            viewModelProviderRepository = Repository()
        )
    }
    private lateinit var adapter: ManagersListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        apiCalls()
        apiResults()
        onClick()
        customQuerySetUp()
    }


    private fun customQuerySetUp() {
        if (viewModel.isQueryNeeded) {
            adapter =
                ManagersListAdapter(
                    detailsListAdapter = viewModel.getDetailsListCustomQuery(
                        viewModel.query
                    )!!
                )
            binding.rvShowList.adapter = adapter
            adapter.notifyDataSetChanged()
            viewVisibility(buttonClickText = false)
        }
    }


    private fun onClick() {
        binding.btnFeatureOne.setOnClickListener {
            if (viewModel.detailsList.isNotEmpty()) {
                Toast.makeText(requireContext(), "Data is already loaded", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewVisibility(buttonClickText = false)
                setUpAdapter()
            }
        }

        binding.btnQueryBuilder.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_customQueryFragment)
        }
    }

    private fun apiCalls() {
        if (viewModel.getDetailsListFromDataBase().isNotEmpty() && !viewModel.isQueryNeeded) {
            adapter =
                ManagersListAdapter(detailsListAdapter = viewModel.getDetailsListFromDataBase())
            binding.rvShowList.adapter = adapter
            viewVisibility(buttonClickText = false)
        } else {
            viewModel.getDepartmentManagers()
            viewModel.getDepartments()
            viewModel.getEmpDepartments()
            viewModel.getEmployees()
            viewModel.getSalaries()
            viewModel.getTitles()
            viewModel.initDataBase(context = requireContext())
        }

    }

    private fun apiResults() {
        viewModel.departmentManagerResponse.observe(viewLifecycleOwner) { listOfDepartmentManager ->
            Log.e("onViewCreated: ", listOfDepartmentManager.toString())
            viewModel.departmentManagerList = listOfDepartmentManager

        }

        viewModel.departments.observe(viewLifecycleOwner) { listOfDepartments ->
            Log.d("onViewCreated", listOfDepartments.toString())
            viewModel.departmentsList = listOfDepartments

        }

        viewModel.empDepartments.observe(viewLifecycleOwner) { listOfEmpDepartments ->
            Log.d("onViewCreated", listOfEmpDepartments.toString())
            viewModel.empDepartmentsList = listOfEmpDepartments
        }

        viewModel.employees.observe(viewLifecycleOwner) { listOfEmployees ->
            Log.d("onViewCreated", listOfEmployees.toString())
            viewModel.employeesList = listOfEmployees
        }


        viewModel.salaries.observe(viewLifecycleOwner) { listOfSalaries ->
            Log.d("onViewCreated", listOfSalaries.toString())
            viewModel.salariesList = listOfSalaries
        }


        viewModel.titles.observe(viewLifecycleOwner) { listOfTitles ->
            Log.d("onViewCreated", listOfTitles.toString())
            viewModel.titlesList = listOfTitles
        }
    }

    private fun setUpAdapter() {
        viewModel.setUpDetails()

        viewVisibility(buttonClickText = false)

        if (viewModel.detailsList.isNotEmpty()) {

            adapter = ManagersListAdapter(
                detailsListAdapter = viewModel.getDetailsListFromDataBase()
            )
            binding.rvShowList.adapter = adapter


        } else {
            Toast.makeText(
                requireContext(),
                "Please wait for data to be loaded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun viewVisibility(buttonClickText: Boolean) {
        binding.tvButtonClick.isVisible = buttonClickText
    }

}