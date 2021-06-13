package com.amityadav.kreditbeeandroidsample.utils

import androidx.recyclerview.widget.DiffUtil
import com.amityadav.kreditbeeandroidsample.domain.model.PhotoItem

class PhotoDiffCallback(private var oldList : ArrayList<PhotoItem>, private var newList : ArrayList<PhotoItem>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList.get(newItemPosition)
    }

}