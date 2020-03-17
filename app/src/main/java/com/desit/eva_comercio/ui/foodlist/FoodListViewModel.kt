package com.desit.eva_comercio.ui.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.Model.FoodModel

class FoodListViewModel : ViewModel() {

    private var mutableFoodLiveData: MutableLiveData<List<FoodModel>>? = null
    fun getMuatableFoodModelListData(): MutableLiveData<List<FoodModel>> {
        if (mutableFoodLiveData == null)
            mutableFoodLiveData = MutableLiveData()
        mutableFoodLiveData!!.value = Common.categoriaseleccionada!!.foods
        return mutableFoodLiveData!!


    }
}