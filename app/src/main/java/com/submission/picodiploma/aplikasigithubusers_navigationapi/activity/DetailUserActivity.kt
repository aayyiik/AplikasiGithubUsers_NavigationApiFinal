package com.submission.picodiploma.aplikasigithubusers_navigationapi.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.picodiploma.aplikasigithubusers_navigationapi.R
import com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter.DetailUserAdapter
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.DetailUserResponse
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.ActivityDetailUserBinding
import com.submission.picodiploma.aplikasigithubusers_navigationapi.fragment.FollowsFragment
import com.submission.picodiploma.aplikasigithubusers_navigationapi.room.Database
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult
import com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel.DetailViewModel


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    private val viewModel by viewModels<DetailViewModel>{
        DetailViewModel.Factory(Database(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ItemsItem>("list")

        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is MyResult.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
                is MyResult.Error -> {
                    Toast.makeText(
                        this, it.exception.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is MyResult.Success<*> -> {
                    val user = it.list as DetailUserResponse
                    binding.imagePhoto.load(user.avatarUrl) {
                        transformations(CircleCropTransformation())
                    }

                    binding.textUsername.text = StringBuilder("@").append(user.login)
                    binding.textName.text = user.name
                    binding.textCompany.text = user.company
                    binding.textLocation.text = user.location
                    binding.tvFollowers.text = user.followers.toString()
                    binding.tvFollowing.text = user.following.toString()
                    binding.tvRepository.text = user.publicRepos.toString()
                    binding.textBio?.text = user.bio?.toString()

                }
            }
        }
        viewModel.detailUser(item?.login?: "")

        val fragments = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
        )

        val judulFragments = mutableListOf(
            getString(R.string.title_followers),
            getString(R.string.title_following)
        )

        val adapter = DetailUserAdapter(this, fragments)
        binding.viewpage2.adapter = adapter

        TabLayoutMediator(
            binding.tab,
            binding.viewpage2)
        { tab, posisi ->
            tab.text = judulFragments[posisi]
        }.attach()

        binding.tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    viewModel.detailFollowers(item?.login?: "")
                }else{
                    viewModel.detailFollowing(item?.login?: "")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewModel.detailFollowers(item?.login?: "")

        viewModel.resultSuccessFavUser.observe(this){
            binding.btnFav.changeIconColor(R.color.redPrimary)
        }
        viewModel.resultDeleteFavUser.observe(this){
            binding.btnFav.changeIconColor(R.color.white)
        }

        binding.btnFav.setOnClickListener{
            viewModel.clickFavorite(item)
        }

        viewModel.catchFavorite(item?.id?: 0){
            binding.btnFav.changeIconColor(R.color.redPrimary)
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

    fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
        val color = ContextCompat.getColor(this.context, color)
        imageTintList = ColorStateList.valueOf(color)
    }


}