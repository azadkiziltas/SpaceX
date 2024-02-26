package com.redstoneapps.spacex.view.homepage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.redstoneapps.spacex.R
import com.redstoneapps.spacex.databinding.ActivityHomePageBinding
import com.redstoneapps.spacex.databinding.ActivityLoginPageBinding
import com.redstoneapps.spacex.utils.gone
import com.redstoneapps.spacex.utils.hide
import com.redstoneapps.spacex.utils.loadFromUrl
import com.redstoneapps.spacex.utils.show
import com.redstoneapps.spacex.view.loginpage.LoginPageActivity
import com.redstoneapps.spacex.view.viewmodel.HomePageViewModel

class HomePageActivity : AppCompatActivity() {
    var viewModel: HomePageViewModel? = null
    var TAG = "___"
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViewModel()
        auth = FirebaseAuth.getInstance()


        binding.apply {

            signOutButton.setOnClickListener {
                auth.signOut()
                val intent = Intent(this@HomePageActivity, LoginPageActivity::class.java)
                startActivity(intent)
                finish()
            }
            binding.refreshButton.setOnClickListener {
                binding.launchLayout.hide()
                binding.imageLayout.hide()
                initViewModel()
            }
        }

    }

    fun initViewModel() {
        viewModel = HomePageViewModel()
        viewModel?.apply {
            launchLiveData.observe(this@HomePageActivity, Observer {
                binding.apply {
                    Log.d(
                        TAG, "onCreate: ${auth.currentUser?.displayName}"
                    )
                    nameTextView.text = it.name
                    flightNumberTextView.text = it.flightNumber.toString()
                    dateTextView.text = convertTime(it.dateUnix)
                    successTextView.text = it.success.toString()

                    launchLayout.show()
                    imageLayout.show()
                    Log.d(TAG, "initViewModel: success")
                    launchImage.loadFromUrl(it.links.patch.small)
                    var youtubeUrl = it.links.webcast
                    var redditUrl = it.links.reddit.launch
                    youtubeImageView.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        startActivity(intent)
                    }
                    redditImageView.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redditUrl))
                        startActivity(intent)
                    }
                }
            })

            error.observe(this@HomePageActivity, Observer {
                it.run {
                    binding.progress.gone()
                    binding.errorLayout.show()


                }
            })
            loading.observe(this@HomePageActivity, Observer {
                if (it) {
                    binding.progress.show()
                }
                else{
                    binding.progress.gone()

                }

            })


        }
    }
}