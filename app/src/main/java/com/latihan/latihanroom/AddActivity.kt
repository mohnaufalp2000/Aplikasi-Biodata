package com.latihan.latihanroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.latihan.latihanroom.database.Constant
import com.latihan.latihanroom.database.User
import com.latihan.latihanroom.database.UserDB
import com.latihan.latihanroom.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private val db by lazy { UserDB(this) }
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        buttonAction()
    }

    private fun buttonAction() {
        binding.btnSave.setOnClickListener {
            val name = binding.edtFullname.text.toString()
            val email = binding.edtEmail.text.toString()

            db.userDao().insertUser(
                User(0, name, email)
            )
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.edtFullname.text.toString()
            val email = binding.edtEmail.text.toString()

            db.userDao().updateUser(
                User(id, name, email)
            )
            finish()
        }
    }

    private fun setupView() {
        val type = intent.getIntExtra(TYPE, 0)

        when(type){
            Constant.TYPE_CREATE -> {}
            Constant.TYPE_READ -> {
                binding.apply {
                    btnSave.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                    textViewTambahUser.visibility = View.GONE
                }
                getUser()
            }
            Constant.TYPE_UPDATE -> {
                binding.apply {
                    btnSave.visibility = View.GONE
                    btnUpdate.visibility = View.VISIBLE
                    textViewTambahUser.text = "Edit User"
                }
                getUser()
            }
        }
    }

    private fun getUser() {
        id = intent.getIntExtra(USER_ID, 0)

        val user = db.userDao().getUser(id)

        binding.apply {
            edtFullname.setText(user.name)
            edtEmail.setText(user.email)
        }

    }

    companion object {
        const val TYPE = "type"
        const val USER_ID = "user_id"
    }
}