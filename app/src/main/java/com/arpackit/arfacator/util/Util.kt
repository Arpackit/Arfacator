package com.arpackit.arfacator.util

import android.content.Context
import android.util.Log
import android.widget.Toast

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.atlassian.onetime.core.TOTPGenerator
import com.atlassian.onetime.model.TOTPSecret


fun showToast(context: Context, text: String, length: Int = 0) = 
    Toast.makeText(context, text, length).show()


fun generateTotp(key: String) = TOTPGenerator()
    .generateCurrent(TOTPSecret.fromBase32EncodedString(key))
    .value


fun schedule(
    delay: Long,
    unit: TimeUnit = if (delay <= 60L) TimeUnit.SECONDS else TimeUnit.MILLISECONDS,
    function: () -> Unit
) {
    Executors
        .newSingleThreadScheduledExecutor()
        .schedule(function, delay, unit)
}


fun scheduleAtFixedRate(
    delay: Long,
    rate: Long,
    unit: TimeUnit = if (delay <= 60L) TimeUnit.SECONDS else TimeUnit.MILLISECONDS,
    function: () -> Unit
) {
    Executors
		.newSingleThreadScheduledExecutor()
		.scheduleAtFixedRate(function, delay, rate, unit)
}