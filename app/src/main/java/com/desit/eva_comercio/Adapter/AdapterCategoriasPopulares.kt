package com.desit.eva_comercio.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bumptech.glide.Glide
import com.desit.eva_comercio.Model.PopularCategoryModel
import com.desit.eva_comercio.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_categorias_populares.view.*


class AdapterCategoriasPopulares(
    internal var context: Context,internal var popularCategoryModels: List<PopularCategoryModel>): RecyclerView.Adapter<AdapterCategoriasPopulares.MiViewHoder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiViewHoder {
        return MiViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_categorias_populares,parent,false))
    }

    override fun getItemCount(): Int {
        return popularCategoryModels.size
    }

    override fun onBindViewHolder(holder: MiViewHoder, position: Int) {
        Glide.with(context).load(popularCategoryModels.get(position).image).into(holder.categori_image!!)
        holder.txt_category_name!!.setText(popularCategoryModels.get(position).name)
    }

    inner class MiViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_category_name:TextView?=null
        var categori_image:CircleImageView?=null

        init {
            txt_category_name=itemView.findViewById(R.id.txt_category_name) as TextView
            categori_image=itemView.findViewById(R.id.category_image) as CircleImageView
        }
    }

}