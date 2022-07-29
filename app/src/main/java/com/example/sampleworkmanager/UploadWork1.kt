package net.simplifiedcoding.workmanagerexample

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.delay


class UploadWork1(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object {
        const val Progress = "Progress"
    }

    override suspend fun doWork(): Result {

        showProgress()
        Log.d("wm" , "hi")
        return Result.success(workDataOf(Progress to 100))
    }

    private suspend fun showProgress()
    {
        for(i in 1..100)
        {
            val progressData = workDataOf(Progress to i)
            setProgress(progressData)
            delay(200L)
            Log.d("data" , progressData.toString())
        }

    }
}
