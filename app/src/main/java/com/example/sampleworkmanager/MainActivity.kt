package com.example.sampleworkmanager

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.example.sampleworkmanager.databinding.ActivityMainBinding
import net.simplifiedcoding.workmanagerexample.UploadWork1

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWork1::class.java)
            .setConstraints(constraints)
            .build()

        val uploadWork2 = OneTimeWorkRequest.Builder(UploadWork2::class.java)
            .setConstraints(constraints)
            .build()



        binding.start.setOnClickListener {

            WorkManager.getInstance(this)
                .beginWith(uploadWork2)
                .then(uploadWorkRequest)
                .enqueue()

            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this) {
                    binding.progressbar.isIndeterminate = true
                    binding.apply {
                        when (it.state) {
                            WorkInfo.State.BLOCKED -> {
                                binding.progressbar.isIndeterminate = false
                                binding.tvProgress.text = it.state.name
                            }
                            WorkInfo.State.ENQUEUED -> {
                                binding.progressbar.isIndeterminate = true
                                val progress = it.progress
                                val data = progress.getInt("Progress", 0)
                                binding.progressbar.setProgress(0, true)
                                binding.progressbar.visibility = View.VISIBLE
                                binding.tvProgress.text = "$data%"
                                binding.tvProgressStatus.text = it.state.name
                            }
                            WorkInfo.State.RUNNING -> {
                                binding.progressbar.isIndeterminate = false
                                val progress = it.progress
                                val data = progress.getInt("Progress", 0)
                                binding.progressbar.setProgress(data, true)
                                binding.progressbar.visibility = View.VISIBLE
                                binding.tvProgress.text = "$data%"
                                binding.tvProgressStatus.text = it.state.name
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                binding.progressbar.isIndeterminate = false
                                val progress = it.progress
                                val data = progress.getInt("Progress", 100)
                                binding.progressbar.setProgress(data, true)
                                binding.progressbar.visibility = View.VISIBLE
                                binding.tvProgress.text = "$data%"
                                binding.tvProgressStatus.text = it.state.name
                            }
                            WorkInfo.State.FAILED -> {
                                binding.progressbar.isIndeterminate = false
                                val progress = it.progress
                                val data = progress.getInt("Progress", 0)
                                binding.progressbar.setProgress(data, true)
                                binding.progressbar.visibility = View.GONE
                                binding.progressbar.isIndeterminate = false
                                binding.tvProgress.text = "$data%"
                                binding.tvProgressStatus.text = it.state.name

                            }
                            WorkInfo.State.CANCELLED -> {
                                binding.progressbar.visibility = View.GONE
                                binding.progressbar.isIndeterminate = false

                            }
                        }
                    }
                }

            WorkManager.getInstance(this).enqueue(uploadWorkRequest)

        }


    }
}