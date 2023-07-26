package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName

data class EmpDepartments(
    @SerializedName("emp_no") val EmpNumber: Int,
    @SerializedName("dept_no") val DepartmentNumber: String,
    @SerializedName("from_date") val startDate: String,
    @SerializedName("to_date") val endDate: String
)
