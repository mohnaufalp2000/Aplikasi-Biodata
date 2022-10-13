package com.latihan.latihanroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.latihanroom.database.Constant
import com.latihan.latihanroom.database.User
import com.latihan.latihanroom.database.UserDB
import com.latihan.latihanroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val db by lazy { UserDB(this) }
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            moveToDetail(0, Constant.TYPE_CREATE)
        }

        showRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun showRecyclerView() {
        userAdapter = UserAdapter(object: UserAdapter.OnAdapterListener{
            override fun onClick(user: User) {
                moveToDetail(user.id, Constant.TYPE_READ)
            }
            override fun onUpdate(user: User) {
                moveToDetail(user.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(user: User) {
                db.userDao().deleteUser(user)
                loadData()
            }
        })
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun loadData() {
        val users = db.userDao().getUsers()
        Log.d("users", users.toString())
        userAdapter.setData(users)
        userAdapter.notifyDataSetChanged()
    }

    private fun moveToDetail(id: Int, type: Int){
        val intent = Intent(this, AddActivity::class.java)
        intent.apply {
            putExtra(AddActivity.TYPE, type)
            putExtra(AddActivity.USER_ID, id)
        }
        startActivity(intent)
    }
}