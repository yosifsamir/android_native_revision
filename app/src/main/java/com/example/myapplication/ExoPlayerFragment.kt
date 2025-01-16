package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.myapplication.databinding.FragmentExoPlayerBinding


class ExoPlayerFragment : Fragment() {

    private lateinit var player: ExoPlayer
    private var _binding: FragmentExoPlayerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = ExoPlayer.Builder(requireContext())
            .build()

        // link the "Player" itself to "UI" components. "Player" notify "UI" when some events happen like playing, seeking.
        binding.playerView.player = player

        // Media3 ExoPlayer converts media items to MediaSource instances that it needs internally.
        // Build the media item.
        val mediaItem = MediaItem.fromUri(Uri.parse("https://storage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"))
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        // Start the playback.
        player.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }

}