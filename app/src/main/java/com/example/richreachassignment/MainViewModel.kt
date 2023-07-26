package com.example.richreachassignment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richreachassignment.models.DepartmentManager
import com.example.richreachassignment.models.Departments
import com.example.richreachassignment.models.EmpDepartments
import com.example.richreachassignment.models.Employees
import com.example.richreachassignment.models.Salaries
import com.example.richreachassignment.models.Titles
import com.example.richreachassignment.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val departmentManagerResponse: MutableLiveData<List<DepartmentManager>> = MutableLiveData()
    val departments: MutableLiveData<List<Departments>> = MutableLiveData()
    val empDepartments: MutableLiveData<List<EmpDepartments>> = MutableLiveData()
    val employees: MutableLiveData<List<Employees>> = MutableLiveData()
    val salaries: MutableLiveData<List<Salaries>> = MutableLiveData()
    val titles: MutableLiveData<List<Titles>> = MutableLiveData()

    fun getDepartmentManagers() {
        viewModelScope.launch(Dispatchers.IO) {
            departmentManagerResponse.postValue(repository.getDepartmentManagers())
        }
    }

    fun getDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            departments.postValue(repository.getDepartments())
        }
    }


    fun getEmpDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            empDepartments.postValue(repository.getEmpDepartments())
        }
    }


    fun getEmployees() {
        viewModelScope.launch(Dispatchers.IO) {
            employees.postValue(repository.getEmployees())
        }
    }

    fun getSalaries() {
        viewModelScope.launch(Dispatchers.IO) {
            salaries.postValue(repository.getSalaries())
        }
    }

    fun getTitles() {
        viewModelScope.launch(Dispatchers.IO) {
            titles.postValue(repository.getTitles())
        }
    }

}