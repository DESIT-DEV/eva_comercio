package com.desit.eva_comercio.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desit.eva_comercio.CallBack.ICategoriaCallBackListener
import com.desit.eva_comercio.CallBack.IClickRecyclerView
import com.desit.eva_comercio.Common.Common
import com.desit.eva_comercio.EventBus.CategoriaClick
import com.desit.eva_comercio.Model.CategoryModel
import com.desit.eva_comercio.R
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus


class AdapterdeCategorias(internal var context: Context,internal var categoriasList: List<CategoryModel>):

    RecyclerView.Adapter<AdapterdeCategorias.MiViewHoder>() {



    inner class MiViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(view: View?) {
            listener!!.paracadaelementoClick(view!!,adapterPosition)
        }
        var Nombre_Categoria: TextView?=null
        var categori_image: ImageView?=null
        internal var listener:IClickRecyclerView?=null

        fun setListener(listener: IClickRecyclerView){
            this.listener=listener
        }
        init {
            Nombre_Categoria=itemView.findViewById(R.id.nombre_categoria) as TextView
            categori_image=itemView.findViewById(R.id.imagen_de_categoria) as ImageView

            itemView.setOnClickListener(this)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterdeCategorias.MiViewHoder {
        return MiViewHoder(LayoutInflater.from(context).inflate(R.layout.layout_categori_item,parent,false))
    }

    override fun getItemCount(): Int {
        return categoriasList.size
    }

    override fun onBindViewHolder(holder: MiViewHoder, position: Int) {
        Glide.with(context).load(categoriasList.get(position).image).into(holder.categori_image!!)
        holder.Nombre_Categoria!!.setText(categoriasList.get(position).name)
        //evento nuevo

        holder.setListener(object : IClickRecyclerView{
            override fun paracadaelementoClick(view: View, pos: Int) {
                Common.categoriaseleccionada=categoriasList.get(pos)
                EventBus.getDefault().postSticky(CategoriaClick(true,categoriasList.get(pos)))
            }

        })

    }

    override fun getItemViewType(position: Int): Int {
        return if (categoriasList.size==1)
                Common.DEFAULT_COLUMN
        else{
            if (categoriasList.size % 2 ==0)
                Common.DEFAULT_COLUMN
            else
                if (position > 1 && position == categoriasList.size-1) Common.FULL_WITH_COLUM else Common.DEFAULT_COLUMN
        }

    }


}