package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName

data class Titles(
    @SerializedName("emp_no") val employeeNumber: Int,
    val title: String,
    @SerializedName("from_date") val startDate: String,
    @SerializedName("to_date") val endDate: String
)
