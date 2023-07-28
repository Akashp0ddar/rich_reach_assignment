package com.example.richreachassignment

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.richreachassignment.database.DetailsDatabase
import com.example.richreachassignment.models.DepartmentManager
import com.example.richreachassignment.models.Departments
import com.example.richreachassignment.models.Details
import com.example.richreachassignment.models.EmpDepartments
import com.example.richreachassignment.models.Employees
import com.example.richreachassignment.models.Salaries
import com.example.richreachassignment.models.Titles
import com.example.richreachassignment.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val repository: Repository) : ViewModel() {
    val departmentManagerResponse: MutableLiveData<List<DepartmentManager>> = MutableLiveData()
    val departments: MutableLiveData<List<Departments>> = MutableLiveData()
    val empDepartments: MutableLiveData<List<EmpDepartments>> = MutableLiveData()
    val employees: MutableLiveData<List<Employees>> = MutableLiveData()
    val salaries: MutableLiveData<List<Salaries>> = MutableLiveData()
    val titles: MutableLiveData<List<Titles>> = MutableLiveData()
    var departmentManagerList: List<DepartmentManager> = listOf()
    var departmentsList: List<Departments> = listOf()
    var empDepartmentsList: List<EmpDepartments> = listOf()
    var employeesList: List<Employees> = listOf()
    var salariesList: List<Salaries> = listOf()
    var titlesList: List<Titles> = listOf()
    var detailsList: ArrayList<Details> = arrayListOf()
    var query = ""
    var isQueryNeeded = false
    private lateinit var database: DetailsDatabase

    fun initDataBase(context: Context) {
        database = DetailsDatabase(context = context)
    }


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


    private fun isEmployeeWorking(employee: Titles): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fromDate = dateFormat.parse(employee.startDate)
        val toDate = dateFormat.parse(employee.endDate)
        val currentDate = Date()

        return fromDate.before(currentDate) && toDate.after(currentDate)
    }


    private fun getYearDifference(fromDate: String, toDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fromDateParsed = dateFormat.parse(fromDate)
        val toDateParsed = dateFormat.parse(toDate)

        val differenceInYears =
            (kotlin.math.abs(toDateParsed.time - fromDateParsed.time) / (365L * 24 * 60 * 60 * 1000)).toInt()
        return "$differenceInYears years"
    }


    fun setUpDetails() {
        if (departmentManagerList.isNotEmpty() && departmentsList.isNotEmpty() && empDepartmentsList.isNotEmpty() && employeesList.isNotEmpty() && salariesList.isNotEmpty() && titlesList.isNotEmpty()) {
            var details: Details

            employeesList.forEach { employee ->
                val matchingTitles = mutableListOf<String>()
                details = Details(
                    employeeName = employee.firstName + " " + employee.lastName,
                    employeeNumber = employee.employeeNumber
                )

                empDepartmentsList.forEach { empDepartments ->
                    if (empDepartments.EmpNumber == employee.employeeNumber) {
                        departmentsList.forEach { departments ->
                            if (empDepartments.DepartmentNumber == departments.departmentNo) {
                                details.employeeDepartment = departments.deptName
                            }
                        }
                    }
                }

                titlesList.forEach { titles ->
                    if (titles.employeeNumber == employee.employeeNumber) {
                        matchingTitles.add(titles.title)
                        details.timeSpentAsManager = getYearDifference(
                            fromDate = titles.startDate,
                            toDate = titles.endDate
                        )
                        details.isActive = isEmployeeWorking(titles)
                    }
                }
                details.employeeTitle = matchingTitles.joinToString(", ")
                detailsList.add(details)
            }

        }
        database.insertDetails(detailsList)
    }

    fun getDetailsListFromDataBase(): ArrayList<Details> {
        return database.getAllDetails()
    }

    fun getDetailsListCustomQuery(queryFromInput: String): ArrayList<Details>? {
        return database.getListOfDetailsWithCustomQuery(query = queryFromInput)
    }

    private var activeNetworkStatusMLD = MutableLiveData<Boolean>()
    val activeNetworkStatus: LiveData<Boolean>
        get() = activeNetworkStatusMLD


    fun networkObservation(context: Context) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("Network", "onAvailable: $network")
                activeNetworkStatusMLD.postValue(true)
            }


            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val unmetered =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)

            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("Network", "onAvailable: $network")
                activeNetworkStatusMLD.postValue(false)
            }
        }

        val connectivityManager = ContextCompat.getSystemService(
            context,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

}