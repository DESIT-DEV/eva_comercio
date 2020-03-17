package com.desit.eva_comercio.ui.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desit.eva_comercio.Adapter.AdapterListaComida
import com.desit.eva_comercio.R

class FoodListFragment : Fragment() {

    private lateinit var foodListViewModel: FoodListViewModel
    var recylcer_food_list:RecyclerView?=null
    var layoutAnimationCloseable:LayoutAnimationController?=null
    var adapter : AdapterListaComida?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foodListViewModel =
            ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_food_list, container, false)
       iniciarView(root)
        foodListViewModel.getMuatableFoodModelListData().observe(this, Observer {
          adapter=AdapterListaComida(context!!,it)
            recylcer_food_list!!.adapter=adapter
            recylcer_food_list!!.layoutAnimation=layoutAnimationCloseable
        })
        return root
    }
    fun iniciarView(root:View?){
        recylcer_food_list=root!!.findViewById(R.id.recylcer_food_list) as RecyclerView
        recylcer_food_list!!.setHasFixedSize(true)
        recylcer_food_list!!.layoutManager=LinearLayoutManager(context)

        layoutAnimationCloseable=AnimationUtils.loadLayoutAnimation(context,R.anim.layout_item_from_left)

    }
}