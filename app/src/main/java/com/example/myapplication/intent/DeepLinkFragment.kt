package com.example.myapplication.intent

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentDeepLinkBinding

class DeepLinkFragment : BaseFragment<FragmentDeepLinkBinding>() {
    private lateinit var link : String
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeepLinkBinding = FragmentDeepLinkBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val navController = findNavController();
//        val navBackStackEntry = navController.getBackStackEntry(R.id.SecondFragment)


        binding.generateLinkBtn.setOnClickListener {
            link = "https://www.myapplication.com"
            binding.link.text = link
        }
        binding.copyBtn.setOnClickListener {
            copyTextToClipboard(link)
        }

    }

    private fun copyTextToClipboard(link: String) {
        val clipboard = requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", link)
        clipboard.setPrimaryClip(clip)
    }


}