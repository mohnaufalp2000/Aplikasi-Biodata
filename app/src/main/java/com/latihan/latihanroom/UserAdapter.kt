package com.latihan.latihanroom

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latihan.latihanroom.database.User
import com.latihan.latihanroom.databinding.RowItemBinding

class UserAdapter(val listener: OnAdapterListener) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val users = ArrayList<User>()

    class ListViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(newUser: List<User>){
        users.clear()
        users.addAll(newUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]

        holder.binding.fullname.text = user.name
        holder.binding.email.text = user.email

        // menekan user
        holder.itemView.setOnClickListener {
            listener.onClick(user)
        }

        // edit
        holder.binding.btnEdit.setOnClickListener {
            listener.onUpdate(user)
        }

        // delete
        holder.binding.btnDelete.setOnClickListener {
            listener.onDelete(user)
        }

    }

    override fun getItemCount(): Int {
        Log.d("size", users.size.toString())
        return users.size
    }

    interface OnAdapterListener{
        fun onClick(user: User)
        fun onUpdate(user: User)
        fun onDelete(user: User)
    }
}