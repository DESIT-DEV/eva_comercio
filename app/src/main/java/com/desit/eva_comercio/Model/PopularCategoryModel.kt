package com.desit.eva_comercio.Model

class PopularCategoryModel {

    var foot_id:String?=null
    var menu_id:String?=null
    var name:String?=null
    var image:String?=null

    constructor()
    constructor(food_id:String?, menu_id:String?, name:String?,iamge:String?){
        this.foot_id=foot_id
        this.menu_id=menu_id
        this.name=name
        this.image=image

    }

}