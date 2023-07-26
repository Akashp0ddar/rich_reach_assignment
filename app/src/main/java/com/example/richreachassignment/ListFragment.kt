package com.example.richreachassignment

import android.os.Bundle
import android.util.Log
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        apiCalls()
        apiResults()

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
        }

        viewModel.departments.observe(viewLifecycleOwner) { listOfDepartments ->
            Log.d("onViewCreated", listOfDepartments.toString())
        }

        viewModel.empDepartments.observe(viewLifecycleOwner) { listOfEmpDepartments ->
            Log.d("onViewCreated", listOfEmpDepartments.toString())
        }

        viewModel.employees.observe(viewLifecycleOwner) { listOfEmployees ->
            Log.d("onViewCreated", listOfEmployees.toString())
        }


        viewModel.salaries.observe(viewLifecycleOwner) { listOfSalaries ->
            Log.d("onViewCreated", listOfSalaries.toString())
        }


        viewModel.titles.observe(viewLifecycleOwner) { listOfTitles ->
            Log.d("onViewCreated", listOfTitles.toString())
        }
    }


}