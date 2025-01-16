package com.example.myapplication.pageView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentTiktokBinding
import com.example.myapplication.databinding.VideoPagerViewItemBinding

class TikTokFragment : BaseFragment<FragmentTiktokBinding>() {
    private val tikTokPageViewAdapter = TikTokPageViewAdapter()
    private var exoPlayerManager: ExoPlayerManager? = null
    private val urls = listOf(
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exoPlayerManager = ExoPlayerManager(requireContext())
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTiktokBinding = FragmentTiktokBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.videosViewPager.adapter = tikTokPageViewAdapter

        tikTokPageViewAdapter.addUrls(
            urls
        )

//        binding.videosViewPager.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            Log.d("DDDDD" , "onScrollChangeListener is called scrollX$scrollX , scrollY$scrollY , oldScrollX$oldScrollX , oldScrollY$oldScrollY")
//        }
        binding.videosViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position) // positionIndex 0,1,2,3,.....
                //I think when the page centralized this will triggered OK.
                Log.d("DDDDD" , "onPageSelected is called position $position")
                /*
                    Then we need to "manager" here . OK.
                    The Manager will control player when you work on the player the UI will notify automatically .
                    The manager will singleton or not ?? not normal class and will create based on the fragment that work on .

                 */
//                binding.videosViewPager.adapter?.notifyItemChanged(position) //
//                binding.videosViewPager[position]?.
                val holder = (binding.videosViewPager.findViewHolderByAdapterPosition(position) as TikTokPageViewViewHolder)
                holder.view.counterTxt.text = "Count : ${position + 1}"
                exoPlayerManager?.play(holder.view.playerViewPagerView , urls[position])
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d("DDDDD" , "onPageScrollStateChanged is called state $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d("DDDDD" , "onPageScrolled is called position $position , positionOffset $positionOffset , positionOffsetPixels $positionOffsetPixels")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayerManager?.release()
        exoPlayerManager = null
    }

}



class TikTokPageViewAdapter : RecyclerView.Adapter<TikTokPageViewViewHolder>() {
    private val urls: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TikTokPageViewViewHolder {
        Log.d("DDDDD" , "onCreateViewHolder is called")
        val view =
            VideoPagerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TikTokPageViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: TikTokPageViewViewHolder, position: Int) {
        Log.d("DDDDD" , "onBindViewHolder is called")
    }

    fun addUrls(urls: List<String>) {
        this.urls.clear()
        this.urls.addAll(urls)
        notifyItemRangeInserted(0, urls.size)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onViewRecycled(holder: TikTokPageViewViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun findRelativeAdapterPositionIn(
        adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
        viewHolder: RecyclerView.ViewHolder,
        localPosition: Int
    ): Int {
        return super.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
    }
}

class TikTokPageViewViewHolder(val view: VideoPagerViewItemBinding) :
    RecyclerView.ViewHolder(view.root)

class ExoPlayerManager(private val context: Context) {
    private var exoPlayer: ExoPlayer? = null

    fun play(playerView: PlayerView , url:String) {
        if (exoPlayer != null) {
            stop()
            release()
        }
        exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer?.setMediaItem(MediaItem.fromUri(url))
        exoPlayer?.prepare()
        exoPlayer?.play()

        playerView.player = exoPlayer
    }

    fun stop() {
        exoPlayer?.stop()
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }

}

private fun ViewPager2.findViewHolderByAdapterPosition(position: Int) : RecyclerView.ViewHolder? {
    return (getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(position) // look to RecyclerViewImpl
}
