package com.submission.picodiploma.aplikasigithubusers_navigationapi.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.submission.picodiploma.aplikasigithubusers_navigationapi.R
import com.submission.picodiploma.aplikasigithubusers_navigationapi.databinding.ActivitySwitchThemeBinding
import com.submission.picodiploma.aplikasigithubusers_navigationapi.preferences.SettingPreferences
import com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel.SettingViewModel

class SwitchThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwitchThemeBinding

    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
            binding.moonLight.changeIconColor(R.color.YelloCute)
            binding.sunLight.changeIconColor(R.color.colorPrimaryDark)
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

    fun ImageView.changeIconColor(@ColorRes color: Int){
        val color = ContextCompat.getColor(this.context, color)
        imageTintList = ColorStateList.valueOf(color)
    }
}