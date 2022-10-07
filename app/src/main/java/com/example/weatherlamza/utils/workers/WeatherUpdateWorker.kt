package com.example.weatherlamza.utils.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherUpdateWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext kotlin.runCatching {
            Log.d("bbb", "doWork: pingam api...")
            Result.success()
        }.getOrDefault(Result.failure())
    }
}
