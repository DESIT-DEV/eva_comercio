package com.desit.eva_comercio.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desit.eva_comercio.CallBack.IClickRecyclerView
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.EventBus.CategoriaClick
import com.desit.eva_comercio.EventBus.ComidaClick
import com.desit.eva_comercio.Model.FoodModel
import com.desit.eva_comercio.R
import org.greenrobot.eventbus.EventBus

class AdapterListaComida(internal var context: Context, internal var foodList: List<FoodModel>):
    RecyclerView.Adapter<AdapterListaComida.MiViewHoder>() {



    inner class MiViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var Nombre_Comida: TextView?=null
        var Precio_comida: TextView?=null
        var imagen_comida: ImageView?=null
        var imagen_Fav: ImageView?=null
        var imagen_carruto: ImageView?=null

        internal var listener:IClickRecyclerView?=null

        fun setListener(listener: IClickRecyclerView){
            this.listener=listener
        }

        init {
            Nombre_Comida=itemView.findViewById(R.id.txt_nombre_comida) as TextView
            Precio_comida=itemView.findViewById(R.id.txt_precio) as TextView
            imagen_comida=itemView.findViewById(R.id.food_image) as ImageView
            imagen_Fav=itemView.findViewById(R.id.img_fav) as ImageView
            imagen_carruto=itemView.findViewById(R.id.img_carrito) as ImageView

            itemView.setOnClickListener(this)

        }

        override fun onClick(view: View?) {
            listener!!.paracadaelementoClick(view!!,adapterPosition)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterListaComida.MiViewHoder {
        return MiViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_food_item,parent,false))
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: AdapterListaComida.MiViewHoder, position: Int) {
        Glide.with(context).load(foodList.get(position).image).into(holder.imagen_comida!!)
        holder.Nombre_Comida!!.setText(foodList.get(position).name)
        holder.Precio_comida!!.setText(foodList.get(position).price.toString())

        //evento de click comida

        holder.setListener(object: IClickRecyclerView{
            override fun paracadaelementoClick(view: View, pos: Int) {
                Common.comidaSeleccionada=foodList.get(pos)
                EventBus.getDefault().postSticky(ComidaClick(true,foodList.get(pos)))
            }

        })
    }







}