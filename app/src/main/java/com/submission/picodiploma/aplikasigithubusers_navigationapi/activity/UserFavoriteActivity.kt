package com.submission.picodiploma.aplikasigithubusers_navigationapi.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter.UsersAdapter
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.ActivityUserFavoriteBinding
import com.submission.picodiploma.aplikasigithubusers_navigationapi.room.Database
import com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel.FavoriteViewModel

class UserFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>{
        FavoriteViewModel.Factory(Database(this))
    }

    private val adapter by lazy{

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFavorites.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvFavorites.layoutManager = LinearLayoutManager(this)

        }
        UsersAdapter { user->
            Intent(this, DetailUserActivity::class.java).apply {
                putExtra("list",user)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getUserFavorite().observe(this){
            adapter.setData(it)
        }
    }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}