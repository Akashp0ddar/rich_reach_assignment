package com.example.richreachassignment.models

import com.google.gson.annotations.SerializedName

data class Departments(
    @SerializedName("dept_no") val departmentNo: String,
    @SerializedName("dept_name") val deptName: String
)
