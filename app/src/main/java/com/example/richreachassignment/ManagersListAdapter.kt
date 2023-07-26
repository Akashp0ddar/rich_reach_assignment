package com.example.richreachassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.richreachassignment.databinding.ManagersSingleItemBinding
import com.example.richreachassignment.models.Details

class ManagersListAdapter(
    val detailsList: List<Details>
) : RecyclerView.Adapter<ManagersListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ManagersSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ManagersSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }
}