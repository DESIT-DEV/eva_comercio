package com.desit.eva_comercio.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.desit.eva_comercio.Model.BestDeals
import com.desit.eva_comercio.R

class AdapterMejoresOfertas(context: Context,itemList:List<BestDeals>,isInfinite:Boolean):LoopingPagerAdapter<BestDeals>(context,itemList,isInfinite) {
    override fun inflateView(viewType: Int, container: ViewGroup?, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.best_deals_item,container!!,false)
    }

    override fun bindView(convertView: View?, listPosition: Int, viewType: Int) {
        val imageView=convertView!!.findViewById(R.id.img_best_deal) as ImageView
        val textView=convertView!!.findViewById<TextView>(R.id.txt_best_deal)

        Glide.with(context).load(itemList[listPosition].image).into(imageView)
        textView.text=itemList[listPosition].name
    }


}
