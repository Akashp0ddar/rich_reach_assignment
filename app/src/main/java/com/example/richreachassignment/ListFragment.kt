package com.example.richreachassignment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.richreachassignment.databinding.FragmentListBinding
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
    }


    private fun onClick() {
        binding.btnFeatureOne.setOnClickListener {
            setUpAdapter()
        }
    }

    private fun apiCalls() {
        viewModel.getDepartmentManagers()
        viewModel.getDepartments()
        viewModel.getEmpDepartments()
        viewModel.getEmployees()
        viewModel.getSalaries()
        viewModel.getTitles()

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
        if (viewModel.detailsList.isNotEmpty()) {
            adapter = ManagersListAdapter(
                detailsListAdapter = viewModel.detailsList
            )
            binding.rvShowList.adapter = adapter
        } else {
            Toast.makeText(
                requireContext(),
                "Please wait for data to be loaded",
                Toast.LENGTH_SHORT
            ).show()
        }
        Log.d("detailsList", "setUpAdapter: ${viewModel.detailsList}")
    }


}