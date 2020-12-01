package pt.atp.playground

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _timerLiveData = MutableLiveData<Long>()
    val timerLiveData = _timerLiveData

    private var timer: CountDownTimer? = null

    fun startTimer(time: Long){
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerLiveData.postValue(millisUntilFinished / 1000)
            }

            override fun onFinish() {

            }
        }
        timer?.start()
    }

}