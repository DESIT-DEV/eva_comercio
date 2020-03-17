package com.desit.eva_comercio.Common

import com.desit.eva_comercio.Model.CategoryModel
import com.desit.eva_comercio.Model.FoodModel
import com.desit.eva_comercio.Model.UserModel

object Common {
    var comidaSeleccionada: FoodModel? = null
    var categoriaseleccionada: CategoryModel?=null
    val FULL_WITH_COLUM: Int =1
    val POPULAR_REF:String="MostPopular"
    val MEJORES_OFERTAS_REF:String="BestDeals"
    val USER_REFENCE="Users"
    val DEFAULT_COLUMN:Int=0
    val CATEGORIA_REF="Category"
    var CurrentUser:UserModel?=null
}