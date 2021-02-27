package com.example.mars.ui.rover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mars.data.model.Photo
import com.example.mars.databinding.ItemPhotoBinding

class PhotoListAdapter : ListAdapter<Photo, PhotoListAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var photoListener: PhotoListener

    fun setPhotoListener(photoListener: PhotoListener){
        this.photoListener = photoListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder  = ViewHolder.create(
        LayoutInflater.from(parent.context),parent,photoListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    class ViewHolder(val binding: ItemPhotoBinding,photoListener: PhotoListener) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                photoListener.clickPhoto(binding.photo)
            }
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, photoListener: PhotoListener): ViewHolder {
                val itemBinding = ItemPhotoBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    itemBinding,
                    photoListener
                )
            }
        }

        fun bind(photo: Photo?) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean = oldItem == newItem
        }
    }

    interface PhotoListener{
        fun clickPhoto(photo: Photo?)
    }

}