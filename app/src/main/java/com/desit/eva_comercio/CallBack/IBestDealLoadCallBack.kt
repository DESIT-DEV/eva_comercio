package com.desit.eva_comercio.CallBack

import com.desit.eva_comercio.Model.BestDeals


interface IBestDealLoadCallBack {
    fun CargamosSimonSimonMejoresOfertas(bestDealsList:List<BestDeals>)
    fun SiFallaLaCargaMejoresOfertas(message:String)
}