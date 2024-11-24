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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NetworkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NetworkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNetworkBinding? = null
    private val binding get() = _binding!!

    private var myAdapter = MyAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentNetworkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myApiDao = RetrofitFactory.createService(MyApiDao::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val currentPrice = myApiDao.getCurrentPrice() // WILL CRUSH

            Log.d("DDDDDDDD","currentPrice: ${currentPrice.body()}")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NetworkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NetworkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
