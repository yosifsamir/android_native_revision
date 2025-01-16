package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    private  var mediaPlayer: MediaPlayer? = null
    private  var mediaPlayer2: MediaPlayer? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondBinding = FragmentSecondBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext() , R.raw.song_1)
    }


    /*
        all of these methods related to MediaPlayer will be work on the main thread .
     */


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonSecond.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
            }
            playSongBtn.setOnClickListener {
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

            stopSongBtn.setOnClickListener {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }

            pauseSongBtn.setOnClickListener {
                mediaPlayer?.pause()
            }

            resumeSongBtn.setOnClickListener {
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(requireContext() , R.raw.song_1)
                }
                mediaPlayer?.start()
            }
            ///////////////
            ///////////////
            ///////////////

            playSongManuallyBtn.setOnClickListener {
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
            stopSongManuallyBtn.setOnClickListener {
                mediaPlayer2?.stop()
                mediaPlayer2?.release()
                mediaPlayer2 = null
            }
            pauseSongManuallyBtn.setOnClickListener {
                mediaPlayer2?.pause()
            }
            resumeSongManuallyBtn.setOnClickListener {
                if(mediaPlayer2 == null){
                    mediaPlayer2 = MediaPlayer()
                    val afd = requireContext().resources.openRawResourceFd(R.raw.song_1)
                    mediaPlayer2?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    mediaPlayer2?.prepare()
                    mediaPlayer2?.start()
                }
                mediaPlayer2?.start()
            }

            goToCoroutinePageBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_coroutineFragment)

            }
            goToFileBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FileFragment)
            }

            goToNetworkBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_NetworkFragment)
            }
            goToExpPlayerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_ExoPlayerFragment)
            }
            goToAlarmBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_AlarmFragment)
            }
            goToNetwrokConnectivityBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_NetworkConnectivityFragment)
            }
            goToServiceBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_ServiceFragment)
            }
            goToSurfaceViewBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_SurfaceViewFragment)
            }
            goToSqlitePageBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_SqliteFragment)
            }
            goToIntentPageBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_IntentFragment)
            }
            goToHomeThreadsBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_HomeThreadsFragment)
            }
            goToCustomViewsBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_CustomViewsFragment)
            }
            goToViewPagerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_PageViewFragment)
            }
            goToTikTokBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_TikTokFragment)
            }
            goToThemeBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_ThemeFragment)
            }
            goToGalleryBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_MyGalleryFragment)
            }
            goToSocketBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_SocketFragment)
            }
            goToDeepLinkBtn.setOnClickListener {
                findNavController().navigate(
                    R.id.action_SecondFragment_to_DeepLinkFragment,
                    null, // Optional: Pass any arguments if needed
                    NavOptions.Builder()
                        .setRestoreState(true) // Ensure that the state is restored
//                        .setPopUpTo(R.id.SecondFragment, true) // Ensure SecondFragment is in the back stack
                        .build()
                )
            }
            goToAnimationsBtn.setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_AnimationsFragment)
            }
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
        Log.d("DDDDD" , "onStop is called")

    }

    override fun onDestroyView() {
        super.onDestroyView() // maybe reach here without onDestroy is called and start again .
        Log.d("DDDDD" , "onDestroyView is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DDDDD" , "onDestroy is called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("DDDDD" , "onDetach is called")
    }


    private fun stopPlayer(mediaPlayer: MediaPlayer){
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}