package com.example.sampleworkmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class UploadWork2(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        showToast()
        return try
        {
            val e = 100 / 0
            Result.success()
        }
        catch (e:Exception)
        {
            Result.failure()
        }
    }

    private suspend fun showToast()
    {
        delay(250L)
    }
}