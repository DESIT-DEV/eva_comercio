package com.desit.eva_comercio.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desit.eva_comercio.CallBack.ICategoriaCallBackListener
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.Model.CategoryModel
import com.desit.eva_comercio.Model.PopularCategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuViewModel : ViewModel(), ICategoriaCallBackListener {
    override fun CargamosSimonSimonCategoriasNormales(categoriaList: List<CategoryModel>) {
        categoriasListMutable!!.value=categoriaList
    }

    override fun SiFallaLaCargaCategoriasNormales(message: String) {
        messageError.value=message
    }

    private var categoriasListMutable : MutableLiveData<List<CategoryModel>>?=null
    private var messageError:MutableLiveData<String> = MutableLiveData()
    private var categoriaCallBackListener: ICategoriaCallBackListener


    init {

        categoriaCallBackListener=this

    }

    fun getCategoriaList():MutableLiveData<List<CategoryModel>>{
        if(categoriasListMutable==null)
        {

            categoriasListMutable= MutableLiveData()
            CargarCategoriaNormal()
        }
        return categoriasListMutable!!
    }

    fun getMensajeError():MutableLiveData<String>{
        return messageError
    }

    private fun  CargarCategoriaNormal (){

        val templList= ArrayList<CategoryModel>()
        val categoriaRef= FirebaseDatabase.getInstance().getReference(Common.CATEGORIA_REF)
        categoriaRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                categoriaCallBackListener.SiFallaLaCargaCategoriasNormales((p0.message))
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(itemSnapShot in p0.children)
                {
                    val model=itemSnapShot.getValue<CategoryModel>(CategoryModel::class.java)
                    model!!.menu_id=itemSnapShot.key
                    templList.add(model)
                }
                categoriaCallBackListener.CargamosSimonSimonCategoriasNormales(templList)
            }

        })
    }
}


