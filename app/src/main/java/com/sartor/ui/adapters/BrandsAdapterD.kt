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
import com.sartor.data.model.BrandStatus
import com.sartor.data.model.Brands
import com.sartor.ui.fragments.discovery_screens.DiscoveryFragmentDirections
import com.sartor.ui.fragments.home_screens.HomeFragmentDirections
import com.sartor.ui.fragments.search.SearchFragmentDirections
import com.sartor.util.constants.Constant
import com.sartor.util.constants.placeImage
import com.sartor.util.constants.showToast

class BrandsAdapterD(private var list: List<BrandResponse>, val context: Activity) :
    RecyclerView.Adapter<BrandsAdapterD.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.child_status, parent, false
            )

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.placeImage(Constant.BASE_MEDIA+list[position].img)
        holder.brandName.text = list[position].title
        val direction = DiscoveryFragmentDirections.actionDiscoveryFragmentToSellerProfileFragment(Gson().toJson(list[position]))

        holder.img.setOnClickListener(Navigation.createNavigateOnClickListener(direction))
        Log.e("BRANDDATA::",list[position].toString())
        /*ADD LONG PRESS FUNCTION*/
        val directionBrands = DiscoveryFragmentDirections.actionDiscoveryFragmentToBrandShowFragment(Gson().toJson(list[position]))
        holder.img.setOnLongClickListener(){
            val activity = context
            Navigation.findNavController(activity,R.id.action_discoveryFragment_to_brandShowFragment).navigate(
                directionBrands
            )
           // Toast.makeText(context, "Long preess", Toast.LENGTH_LONG).show()

            true

        }

    }


    override fun getItemCount(): Int {
        Log.i("Discovery_", list.size.toString())
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val brandName: TextView = itemView.findViewById(R.id.brandName)
    }
}