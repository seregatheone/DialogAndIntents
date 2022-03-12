package com.example.a16

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class CrimeDetailedViewModel() : ViewModel() {
    val crimeDetailedLiveData by lazy{
        MutableLiveData(ModelCrime(1,"Murder", "${LocalDateTime.now().dayOfYear}",LocalDateTime.now().hour,LocalDateTime.now().minute,false))
    }

}