package com.sartor.ui.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sartor.R
import com.sartor.data.api.response.ProductResponse
import com.sartor.data.model.Product
import com.sartor.data.model.ProductDetails
import com.sartor.ui.fragments.home_screens.HomeFragmentDirections
import com.sartor.util.constants.Constant
import com.sartor.util.constants.placeImage

class ProductsAdapter(private var list: List<ProductResponse>, private val context: Context) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.child_home_screen2, parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.name.text = list[position].name
//        holder.price.text = list[position].price
//        holder.store.text = list[position].store


        Log.d("_productId", list[position].id)

        holder.img.placeImage(Constant.BASE_MEDIA+list[position].img.img0)

        val action = HomeFragmentDirections.actionHomeFragment2ToProductsDescriptionFragment(
            ProductDetails(
                list[position].store.full_name,
                list[position].store.picture,
                list[position].name,
                list[position].price,
                list[position].rating,
                list[position].description,
                listOf(
                    list[position].img.img0,
                    list[position].img.img1,
                    list[position].img.img2,
                    list[position].img.img3,
                    list[position].img.img4,
                    list[position].img.img5,
                    list[position].img.img6,
                    list[position].img.img7,
                ),
                list[position].id,

            )
        )
        holder.img.setOnClickListener (
            Navigation.createNavigateOnClickListener(action)
//            Navigation.findNavController(context as Activity, R.id.homeHost).navigate(
//                R.id.action_homeFragment2_to_productsDescriptionFragment
//            )
        )

//        holder.container.setOnClickListener {
//            Navigation.findNavController(context as Activity, R.id.homeHost).navigate(
//                R.id.action_homeFragment2_to_productsDescriptionFragment
//            )
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var name: TextView = itemView.findViewById(R.id.tvTitle)
//        var store: TextView = itemView.findViewById(R.id.tvStore)
//        var price: TextView = itemView.findViewById(R.id.tvPrice)
        var img: ImageView = itemView.findViewById(R.id.img)
//        var container: ConstraintLayout = itemView.findViewById(R.id.lyContainer)
    }
}