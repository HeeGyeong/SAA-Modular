package com.example.main

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.fragment.app.Fragment
import com.example.main.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dynamicShortcut.setOnClickListener {
            addDynamicShortCuts()
        }

        binding.pinShortcut.setOnClickListener {
            addPinShortCuts()
        }

        binding.removeDynamic.setOnClickListener {
            removeDynamicShortCuts()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun addDynamicShortCuts() {
        val shortcut = ShortcutInfoCompat.Builder(requireActivity(), "blog_shortcuts")
            .setShortLabel("Heeg's Blog")
            .setLongLabel("Heeg's Blog")
            .setIcon(IconCompat.createWithResource(requireActivity(),
                R.drawable.ic_launcher_foreground))
            .setIntent(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://heegs.tistory.com/")))
            .build()

        ShortcutManagerCompat.pushDynamicShortcut(requireActivity(), shortcut)

        Toast.makeText(activity, "Shortcut ????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
    }

    private fun removeDynamicShortCuts() {
        val shortCutList = listOf("blog_shortcuts")
        ShortcutManagerCompat.removeDynamicShortcuts(requireActivity(), shortCutList)

//        ShortcutManagerCompat.removeAllDynamicShortcuts(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addPinShortCuts() {
        val shortcutManager = requireActivity().getSystemService(ShortcutManager::class.java)

        if (shortcutManager!!.isRequestPinShortcutSupported) {

            val shortCutCount = shortcutManager.pinnedShortcuts.size
            Log.d("shortCutLog", " size ? $shortCutCount")

            var isExist = false
            if (shortCutCount > 0) {
                for (index in 0 until shortCutCount) {
                    Log.d("shortCutLog", " id ? ${shortcutManager.pinnedShortcuts[index].id}")
                    Log.d("shortCutLog", " shortLabel ? ${shortcutManager.pinnedShortcuts[index].shortLabel}")
                    Log.d("shortCutLog", " longLabel ? ${shortcutManager.pinnedShortcuts[index].longLabel}")

                    if (shortcutManager.pinnedShortcuts[index].id == "pin-shortcut") {
                        isExist = true
                    }
                }
            }

            if (isExist) {
                Toast.makeText(activity, "?????? ???????????? Shortcut?????????.", Toast.LENGTH_SHORT).show()
                return
            }

            val sendData = Bundle().also {
                it.putString("destination", "MoveFragment")
                it.putString("other", "sample")
            }

            val pinShortcutInfo = ShortcutInfo.Builder(context, "pin-shortcut")
                .setShortLabel("MoveFragment")
                .setLongLabel("MoveFragment")
                .setIntent(
                    Intent().run {
                        action = Intent.ACTION_SEND
                        putExtras(sendData)
                        setClass(requireActivity(), requireActivity()::class.java)
                    }
                )
                .build()
            val pinnedShortcutCallbackIntent =
                shortcutManager.createShortcutResultIntent(pinShortcutInfo)

            // Google Sample?????? Flag?????? 0?????? ???????????? ?????????,
            // 31 ?????? ????????? ???????????? ??????, FLAG ?????? IMMUTABLE, MUTABLE ??? ????????? ????????? ????????? ?????????. IMMUTABLE??? ??????,
            val successCallback = PendingIntent.getBroadcast(context, /* request code */ 0,
                pinnedShortcutCallbackIntent, FLAG_IMMUTABLE)

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                successCallback.intentSender)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
