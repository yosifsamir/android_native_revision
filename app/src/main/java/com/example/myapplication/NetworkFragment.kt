package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentNetworkBinding
import com.example.myapplication.databinding.MyViewItemBinding
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkFragment : BaseFragment<FragmentNetworkBinding>() {
    private var myAdapter = MyAdapter()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNetworkBinding  = FragmentNetworkBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myApiDao = RetrofitFactory.createService(MyApiDao::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val currentPrice = myApiDao.getCurrentPrice() // WILL CRUSH

                Log.d("DDDDDDDD","currentPrice: ${currentPrice.body()}")
            } catch (exception : Exception){
                Log.d("DDDDD" , "exception: $exception")
            }

        }
        binding.myRecyclerView.apply {
            adapter = myAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator().apply {
                changeDuration = 0
                moveDuration = 0
                removeDuration = 0
                addDuration = 1000
            }
        }
        binding.addNameBtn.setOnClickListener {
            myAdapter.addItem("Youssef")
            binding.myRecyclerView.scrollToPosition(myAdapter.itemCount - 1)
        }


//        Thread {
//            val currentPrice = myApiDao.getCurrentPrice() // WILL CRUSH
//
//            print("currentPrice: $currentPrice")
//        }.start()
    }



}

object RetrofitFactory{
    private val retrofitClient = Retrofit.Builder()
        .baseUrl("https://api.coindesk.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            // You can customize many things here . like Timeout , Interceptors , etc
            .build())
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofitClient.create(serviceClass)
    }

}

interface MyApiDao{

    @GET("/v1/bpi/currentprice.json")
    suspend fun getCurrentPrice(): Response<MyModel>
}


data class MyModel(@SerializedName("time") var time: MyTime? = null)
data class MyTime(
    @SerializedName("updated") var updated: String? = null,
    @SerializedName("updatedISO") var updatedISO: String? = null,
    @SerializedName("updateduk") var updateduk: String? = null
)

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private val names: MutableList<String> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.apply {
            nameTxt.text = names[position]
            descTxt.text = "Desc"
        }

    }

    fun addItem(data : String){
        names.add(data)
        notifyItemInserted(names.size - 1)
//        notifyItemRangeChanged(names.size - 1 , names.size)
    }

    override fun getItemCount(): Int {
        return names.size
    }
}

class MyViewHolder(val view: MyViewItemBinding) : RecyclerView.ViewHolder(view.root)
