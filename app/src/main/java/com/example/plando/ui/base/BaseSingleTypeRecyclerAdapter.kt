package com.example.plando.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


open class BaseSingleTypeRecyclerAdapter<DataType : Any, VB : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) :
    RecyclerView.Adapter<BaseSingleTypeRecyclerAdapter<DataType, VB>.ViewHolder>() {
    private var dataList = listOf<DataType>()

    open fun bind(binding: VB, item: DataType, id: Int) {}

    inner class ViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bindVH(item: DataType, id: Int) {
            bind(binding, item, id)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(bindingInflater(LayoutInflater.from(viewGroup.context), viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bindVH(dataList[position], position)

    override fun getItemCount() = dataList.size

    fun setData(list: List<DataType>) {
        dataList = list

        notifyDataSetChanged()
    }

    fun addData(item: DataType) {
        dataList = dataList + item
        notifyItemInserted(dataList.size - 1)
    }
}