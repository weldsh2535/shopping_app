package com.sartor.ui.adapters

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sartor.R
import com.sartor.data.api.response.BrandResponse
import com.sartor.data.api.response.ProductResponse
import com.sartor.ui.fragments.blog_screens.BlogFragmentDirections
import com.sartor.ui.fragments.home_screens.HomeFragmentDirections
import com.sartor.util.constants.Constant
import com.sartor.util.constants.placeImage

class BrandsAdapter(private var list: List<BrandResponse>, val context: Activity) : RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.child_status, parent,false
            )

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.img.placeImage(Constant.BASE_MEDIA + list[position].img)
        holder.img.placeImage(list[position].img)
        holder.title.text = list[position].title
       Log.e("BRAND::",list[position].toString());
        val direction = HomeFragmentDirections.actionHomeFragment2ToSellerProfileFragment(Gson().toJson(list[position]))

        val directionBrands = HomeFragmentDirections.actionHomeFragment2ToBrandShowFragment(Gson().toJson(list[position]))

        holder.img.setOnClickListener (Navigation.createNavigateOnClickListener(direction))
        /*ADD LONG PRESS FUNCTION*/
        holder.img.setOnLongClickListener(){
            val activity = context as Activity
            Navigation.findNavController(activity,R.id.action_homeFragment2_to_brandShowFragment).navigate(
                directionBrands
            )
            //Toast.makeText(context, "Long preess", Toast.LENGTH_LONG).show()

            true

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val title : TextView = itemView.findViewById(R.id.brandName)
    }
}