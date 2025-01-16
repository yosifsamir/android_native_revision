package com.example.myapplication.pageView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentPageViewBinding
import com.example.myapplication.databinding.PagerViewItemBinding

class PageViewFragment : BaseFragment<FragmentPageViewBinding>() {
    private var pageViewAdapter = PageViewAdapter()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPageViewBinding = FragmentPageViewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = pageViewAdapter
        pageViewAdapter.addAllPatients(
            listOf("Yousef", "Ahmed", "Hassan", "Yousef", "Ahmed", "Hassan", "Yousef", "Ahmed", "Hassan", "Yousef", "Ahmed", "Hassan")
        )

    }

}
class PageViewAdapter : RecyclerView.Adapter<PageViewViewHolder>() {
    private val names: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewViewHolder {
        val view = PagerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewViewHolder, position: Int) {
        holder.view.apply {
            name.text = names[position]
        }

    }

    fun addAllPatients(names : List<String>){
        this.names.clear()
        this.names.addAll(names)
        notifyItemRangeInserted(0,names.size)
    }

    override fun getItemCount(): Int {
        return names.size
    }
}

class PageViewViewHolder(val view: PagerViewItemBinding) : RecyclerView.ViewHolder(view.root)