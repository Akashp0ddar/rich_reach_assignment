package com.example.richreachassignment.api

import com.example.richreachassignment.models.DepartmentManager
import com.example.richreachassignment.models.Departments
import com.example.richreachassignment.models.EmpDepartments
import com.example.richreachassignment.models.Employees
import com.example.richreachassignment.models.Salaries
import com.example.richreachassignment.models.Titles
import retrofit2.http.GET
import retrofit2.http.Header

interface SimpleApi {

    @GET("department_manager.json?alt=media")
    suspend fun getDepartmentManagers(@Header("Authorization") token: String): List<DepartmentManager>

    @GET("departments.json?alt=media")
    suspend fun getDepartments(@Header("Authorization") token: String): List<Departments>


    @GET("emp_departments.json?alt=media")
    suspend fun getEmployeeDepartments(@Header("Authorization") token: String): List<EmpDepartments>

    @GET("employees.json?alt=media")
    suspend fun getEmployees(@Header("Authorization") token: String): List<Employees>

    @GET("salaries.json?alt=media")
    suspend fun getSalaries(@Header("Authorization") token: String): List<Salaries>

    @GET("titles.json?alt=media")
    suspend fun getTitles(@Header("Authorization") token: String): List<Titles>
}