package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName

data class Salaries(
    @SerializedName("emp_no") val employeeNumber: Int,
    val salary: Int,
    @SerializedName("from_date") val startDate: String,
    @SerializedName("to_date") val endDate: String
)
