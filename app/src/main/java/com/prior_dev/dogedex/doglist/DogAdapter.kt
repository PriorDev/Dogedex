package com.prior_dev.dogedex.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var onItemClickListener: ((Dog) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: ((Dog) -> Unit)){
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener: ((Dog) -> Unit)? = null
    fun setOnLongItemClickListener(onItemLongListener: ((Dog) -> Unit)){
        this.onLongItemClickListener = onItemLongListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))

        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.bind(dog)
    }

    inner class DogViewHolder(
        private val binding: DogListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog){
            if(dog.inCollection){
                binding.dogImage.visibility = View.VISIBLE

                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_background
                )

                binding.dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }

                binding.dogListItemLayout.setOnLongClickListener {
                    onLongItemClickListener?.invoke(dog)
                    true
                }

                binding.dogImage.load(dog.imageUrl)
                binding.dogIndex.visibility = View.GONE
            }else{
                binding.dogImage.visibility = View.GONE
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_null_background
                )
                binding.dogIndex.visibility = View.VISIBLE
                binding.dogIndex.text = dog.index.toString()
            }
        }
    }
}