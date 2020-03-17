package com.desit.eva_comercio.ui.home

import android.renderscript.Sampler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desit.eva_comercio.CallBack.IBestDealLoadCallBack
import com.desit.eva_comercio.CallBack.IPopularLoadCallBack
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.Model.BestDeals
import com.desit.eva_comercio.Model.PopularCategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel(), IPopularLoadCallBack, IBestDealLoadCallBack {

    override fun CargamosSimonSimonListaPopular(popularModelList: List<PopularCategoryModel>){
        popularListMutableLiveData!!.value=popularModelList
    }

    override fun SiFallaLaCargaPopularLista(message:String){
        messaError.value=message
    }

    override fun CargamosSimonSimonMejoresOfertas(bestDealsList: List<BestDeals>) {
        MejoresOfertasMutableLiveData!!.value=bestDealsList
    }

    override fun SiFallaLaCargaMejoresOfertas(message: String) {
        messaError.value=message
    }

    private  var popularListMutableLiveData: MutableLiveData<List<PopularCategoryModel>>?=null
    private  var MejoresOfertasMutableLiveData: MutableLiveData<List<BestDeals>>?=null
    private lateinit var messaError: MutableLiveData<String>
    private  var popularLoadCallBackListener: IPopularLoadCallBack
    private  var BestDealCallBackListener:IBestDealLoadCallBack
    val bestDealListener:LiveData<List<BestDeals>>
    get(){
        if (MejoresOfertasMutableLiveData==null)
        {
            MejoresOfertasMutableLiveData= MutableLiveData()
            messaError= MutableLiveData()
            CargarMejoresOferta()
        }
        return MejoresOfertasMutableLiveData!!
    }

    private fun CargarMejoresOferta(){
        val templList= ArrayList<BestDeals>()
        val bestDealRef=FirebaseDatabase.getInstance().getReference(Common.MEJORES_OFERTAS_REF)

        bestDealRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                BestDealCallBackListener.SiFallaLaCargaMejoresOfertas((p0.message))
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(itemSnapShot in p0.children)
                {
                    val model=itemSnapShot.getValue<BestDeals>(BestDeals::class.java)
                    templList.add(model!!)
                }
                BestDealCallBackListener.CargamosSimonSimonMejoresOfertas(templList)
            }

        })
    }

    val popularList:LiveData<List<PopularCategoryModel>>

    get() {
        if (popularListMutableLiveData==null)
        {
            popularListMutableLiveData= MutableLiveData()
            messaError= MutableLiveData()
            CargarListaPopular()
        }
        return popularListMutableLiveData!!
    }

    private fun CargarListaPopular() {
        val templList= ArrayList<PopularCategoryModel>()
        val popularRef=FirebaseDatabase.getInstance().getReference(Common.POPULAR_REF)
        popularRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                popularLoadCallBackListener.SiFallaLaCargaPopularLista((p0.message))
            }

            override fun onDataChange(p0: DataSnapshot) {
               for(itemSnapShot in p0.children)
               {
                   val model=itemSnapShot.getValue<PopularCategoryModel>(PopularCategoryModel::class.java)
                   templList.add(model!!)
               }
                popularLoadCallBackListener.CargamosSimonSimonListaPopular(templList)
            }

        })
    }

    init {
        popularLoadCallBackListener=this
        BestDealCallBackListener=this
    }


}