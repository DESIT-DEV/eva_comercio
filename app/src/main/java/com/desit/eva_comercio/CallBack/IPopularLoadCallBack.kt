package com.desit.eva_comercio.CallBack

import com.desit.eva_comercio.Model.PopularCategoryModel

interface IPopularLoadCallBack {

    fun CargamosSimonSimonListaPopular(popularModelList:List<PopularCategoryModel>)
    fun SiFallaLaCargaPopularLista(message:String)
}