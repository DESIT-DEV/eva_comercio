package com.desit.eva_comercio.CallBack

import com.desit.eva_comercio.Model.CategoryModel

interface ICategoriaCallBackListener {
    fun CargamosSimonSimonCategoriasNormales(categoriaList:List<CategoryModel>)
    fun SiFallaLaCargaCategoriasNormales(message:String)
}