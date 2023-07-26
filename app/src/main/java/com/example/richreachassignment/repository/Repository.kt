package com.example.richreachassignment.repository

import com.example.richreachassignment.api.RetrofitInstance
import com.example.richreachassignment.models.DepartmentManager
import com.example.richreachassignment.models.Departments
import com.example.richreachassignment.models.EmpDepartments
import com.example.richreachassignment.models.Employees
import com.example.richreachassignment.models.Salaries
import com.example.richreachassignment.models.Titles

class Repository {
    suspend fun getDepartmentManagers(): List<DepartmentManager> {
        return RetrofitInstance.api.getDepartmentManagers("36ff273d-6c1b-423a-88e9-039012db5091")
    }

    suspend fun getDepartments(): List<Departments> {
        return RetrofitInstance.api.getDepartments("708382fc-1472-4a41-9c24-3b2190cf23fe")
    }

    suspend fun getEmpDepartments(): List<EmpDepartments> {
        return RetrofitInstance.api.getEmployeeDepartments("5c4f0a44-1410-477f-8493-d14a3971caf1")
    }

    suspend fun getEmployees(): List<Employees> {
        return RetrofitInstance.api.getEmployees("f6316a65-1202-4169-9062-32da5f96a38c")
    }

    suspend fun getSalaries(): List<Salaries> {
        return RetrofitInstance.api.getSalaries("c5d269b6-0e1d-4b85-8d61-8c4a8e4bd7c1")
    }

    suspend fun getTitles(): List<Titles> {
        return RetrofitInstance.api.getTitles("bd295b48-6d2c-480a-ab42-f086ad15a1a0")
    }

}