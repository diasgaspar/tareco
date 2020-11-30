package com.gog.tareco

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private var timer: CountDownTimer? = null
    private val _timerLiveDate = MutableLiveData<Long>()
    val timerLiveData : MutableLiveData<Long> = _timerLiveDate
    fun starTimer(time : Long){
        timer = object : CountDownTimer(time, 1000){
            override fun onFinish() {
                
            }


            override fun onTick(millisUntilFinished: Long) {
                _timerLiveDate.postValue(millisUntilFinished / 1000)
            }


        }
        timer?.start()


    }
}