package taki.eddine.premier.league.pro.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {

    var homeDetailsLiveData = MutableLiveData<Map<String,String>>()
    var awayDetailsLiveData = MutableLiveData<Map<String,String>>()


    fun sendHomeDetails(map : HashMap<String,String>){
        homeDetailsLiveData.value = map
    }
    fun sendAwayDetails(map : HashMap<String,String>){
        awayDetailsLiveData.value = map
    }
}