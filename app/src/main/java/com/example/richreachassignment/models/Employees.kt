package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName

data class Employees(
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("emp_no") val employeeNumber: Int,
    @SerializedName("first_name") val firstName: String,
    val gender: String,
    @SerializedName("hire_date") val hireDate: String,
    @SerializedName("last_name") val lastName: String
)