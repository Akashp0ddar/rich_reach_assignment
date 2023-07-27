package com.example.richreachassignment.models

data class Details(
    var employeeName: String? = null,
    var employeeNumber: Int? = null,
    var employeeTitle: String? = null,
    var employeeDepartment: String? = null,
    var timeSpentAsManager: String? = null,
    var isActive: Boolean? = null
)
