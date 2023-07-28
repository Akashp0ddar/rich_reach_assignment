package com.example.richreachassignment.fragments.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.richreachassignment.databinding.ManagersSingleItemBinding
import com.example.richreachassignment.models.Details

class ManagersListAdapter(
    private val detailsListAdapter: ArrayList<Details>
) : RecyclerView.Adapter<ManagersListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ManagersSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ManagersSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvEmployeeNameResult.text = detailsListAdapter[position].employeeName
        holder.binding.tvTitleResult.text = detailsListAdapter[position].employeeTitle
        holder.binding.tvTimeAsManagerResult.text = detailsListAdapter[position].timeSpentAsManager
        holder.binding.tvDepartmentResult.text = detailsListAdapter[position].employeeDepartment
        holder.binding.tvcurrentlyActiveResult.text =
            if (detailsListAdapter[position].isActive == true) {
                "Active"
            } else {
                "Inactive"
            }

    }

    override fun getItemCount(): Int {
        return detailsListAdapter.size
    }


}