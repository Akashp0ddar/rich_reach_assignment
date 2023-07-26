package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName


data class DepartmentManager(
    @SerializedName("emp_no") val departmentNo: Int,
    @SerializedName("dept_no") val employeeNo: String,
    @SerializedName("from_date") val fromDate: String,
    @SerializedName("to_date") val toDate: String,
)
