package com.amityadav.kreditbeeandroidsample.presentation.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amityadav.kreditbeeandroidsample.R
import com.amityadav.kreditbeeandroidsample.databinding.PhotoItemBinding
import com.amityadav.kreditbeeandroidsample.domain.model.PhotoItem
import com.amityadav.kreditbeeandroidsample.utils.Model
import com.amityadav.kreditbeeandroidsample.utils.PhotoDiffCallback

class PhotosAdapter(private var photosArrayList: ArrayList<PhotoItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: PhotoItemBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Model.FOOTER_TYPE) {
            val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_row_progress, viewGroup, false)
            ProgressViewHolder(v)
        } else{
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.photo_item, viewGroup, false)
            return ItemAdapterViewHolder(binding)
        }
    }

    class ProgressViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var progressView: ProgressBar = itemView.findViewById(R.id.progressbar) as ProgressBar
        internal var reloadApi: RelativeLayout = itemView.findViewById(R.id.reload_api) as RelativeLayout
    }

    override fun getItemViewType(position: Int): Int {
        return if (photosArrayList[position].id == "-1") {
            Model.FOOTER_TYPE
        }else{
            Model.MAIN_TYPE
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val item = photosArrayList[position]

        if (item.id=="-1"){
            viewHolder as ProgressViewHolder
            if (item.title == "-1") {
                viewHolder.reloadApi.visibility = View.VISIBLE
                viewHolder.progressView.visibility = View.GONE
                viewHolder.reloadApi.setOnClickListener {

                }
            } else {
                viewHolder.reloadApi.visibility = View.GONE
                viewHolder.progressView.visibility = View.VISIBLE
            }
        }else {
            viewHolder as ItemAdapterViewHolder
            viewHolder.bindUser(position)
        }
    }

    override fun getItemCount(): Int {
        return photosArrayList.size
    }

    inner class ItemAdapterViewHolder(var binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindUser(position: Int) {
            val lModel = photosArrayList[position]
            binding.ivImage.load(lModel.thumbnailUrl + ".jpg")
            binding.title.text = lModel.title

            binding.ivImage.setOnClickListener { v: View? ->
            }
        }
    }

    fun addNullData() {
        if (!photosArrayList.isNullOrEmpty() && photosArrayList[photosArrayList.size - 1].id != "-1") {
            val item = PhotoItem()
            item.id = "-1"
            photosArrayList.add(item)
            notifyItemInserted(photosArrayList.size - 1)
        }
    }

    fun removeNull() {
        if (!photosArrayList.isNullOrEmpty() && photosArrayList[photosArrayList.size - 1].id == "-1") {
            photosArrayList.removeAt(photosArrayList.size - 1)
            notifyItemRemoved(photosArrayList.size)
        }
    }

    fun showRetry() {
        if(!photosArrayList.isNullOrEmpty()) {
            photosArrayList[photosArrayList.size - 1].title = "-1"
            notifyItemChanged(photosArrayList.size - 1)
        }
    }

    fun setDataList(data: ArrayList<PhotoItem>) {
        photosArrayList = data
        notifyDataSetChanged()
    }

    fun addAllData(data: ArrayList<PhotoItem>) {
        val oldList = photosArrayList
        photosArrayList = data
        val diffResult = DiffUtil.calculateDiff(PhotoDiffCallback(oldList, photosArrayList))
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearDataList() {
        photosArrayList.clear()
        notifyDataSetChanged()
    }
}