package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var mediaPlayer: MediaPlayer? = null
    private  var mediaPlayer2: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext() , R.raw.song_1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }
    /*
        all of these methods related to MediaPlayer will be work on the main thread .
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
        }
        binding.playSongBtn.setOnClickListener {
            // What you want to do here ?
            /*
                We want to some logic here .
                like playing audio(song). Ok I think the MediaPlayer has
                state lifecycle OK .

                if mediaPlayer is stop need to start everything from scratch
             */

            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(requireContext() , R.raw.song_1)
            }
            mediaPlayer?.start()
        }

        binding.stopSongBtn.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        binding.pauseSongBtn.setOnClickListener {
            mediaPlayer?.pause()
        }

        binding.resumeSongBtn.setOnClickListener {
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(requireContext() , R.raw.song_1)
            }
            mediaPlayer?.start()
        }
        ///////////////
        ///////////////
        ///////////////

        binding.playSongManuallyBtn.setOnClickListener {
            if(mediaPlayer2 != null){
                mediaPlayer2?.start()
            }else{
                mediaPlayer2 = MediaPlayer()
                val afd = requireContext().resources.openRawResourceFd(R.raw.song_1)
                mediaPlayer2?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                mediaPlayer2?.prepare()
                mediaPlayer2?.start()
            }
        }
        binding.stopSongManuallyBtn.setOnClickListener {
            mediaPlayer2?.stop()
            mediaPlayer2?.release()
            mediaPlayer2 = null
        }
        binding.pauseSongManuallyBtn.setOnClickListener {
            mediaPlayer2?.pause()
        }
        binding.resumeSongManuallyBtn.setOnClickListener {
            if(mediaPlayer2 == null){
                mediaPlayer2 = MediaPlayer()
                val afd = requireContext().resources.openRawResourceFd(R.raw.song_1)
                mediaPlayer2?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                mediaPlayer2?.prepare()
                mediaPlayer2?.start()
            }
            mediaPlayer2?.start()
        }

        binding.goToCoroutinePageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_coroutineFragment)

        }
        binding.goToFileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FileFragment)
        }

        binding.goToNetworkBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_NetworkFragment)
        }
        binding.goToExpPlayerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ExoPlayerFragment)
        }
        binding.goToAlarmBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_AlarmFragment)
        }
        binding.goToNetwrokConnectivityBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_NetworkConnectivityFragment)
        }

    }

    override fun onStop() {
        // separate these things two separate function
        if(mediaPlayer != null){
            stopPlayer(mediaPlayer!!)
            mediaPlayer = null
        }
       if(mediaPlayer2 != null){
           stopPlayer(mediaPlayer2!!)
           mediaPlayer2 = null
       }

        super.onStop()
    }

    private fun stopPlayer(mediaPlayer: MediaPlayer){
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}