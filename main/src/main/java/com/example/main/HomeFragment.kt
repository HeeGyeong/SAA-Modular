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
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d("viewModelData", "HomeFragment onCreateView Call 1")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("viewModelData", "HomeFragment onCreateView Call 2")

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("viewModelData", "HomeFragment onViewCreated Call 3")

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
        Log.d("viewModelData", "HomeFragment onActivityCreated Call 4")

        viewModel.addData()
        Log.d("viewModelData", "HomeFragment Data ? ${viewModel.checkData}")
    }

    private fun addDynamicShortCuts() {
        val shortcut = ShortcutInfoCompat.Builder(requireActivity(), "blog_shortcuts")
            .setShortLabel("Heeg's Blog")
            .setLongLabel("Heeg's Blog")
            .setIcon(
                IconCompat.createWithResource(
                    requireActivity(),
                    R.drawable.ic_launcher_foreground
                )
            )
            .setIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://heegs.tistory.com/")
                )
            )
            .build()

        ShortcutManagerCompat.pushDynamicShortcut(requireActivity(), shortcut)

        Toast.makeText(activity, "Shortcut 종류가 추가 되었습니다.", Toast.LENGTH_SHORT).show()
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

            var isExist = false
            if (shortCutCount > 0) {
                for (index in 0 until shortCutCount) {
                    Log.d("shortCutLog", " id ? ${shortcutManager.pinnedShortcuts[index].id}")
                    Log.d(
                        "shortCutLog",
                        " shortLabel ? ${shortcutManager.pinnedShortcuts[index].shortLabel}"
                    )
                    Log.d(
                        "shortCutLog",
                        " longLabel ? ${shortcutManager.pinnedShortcuts[index].longLabel}"
                    )

                    if (shortcutManager.pinnedShortcuts[index].id == "pin-shortcut") {
                        isExist = true
                    }
                }
            }

            if (isExist) {
                Toast.makeText(activity, "이미 존재하는 Shortcut입니다.", Toast.LENGTH_SHORT).show()
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

            // Google Sample에서 Flag값은 0으로 설정되어 있지만,
            // 31 버전 이상을 타게팅할 경우, FLAG 값은 IMMUTABLE, MUTABLE 중 하나로 반드시 설정이 필요함. IMMUTABLE을 권장,
            val successCallback = PendingIntent.getBroadcast(
                context, /* request code */ 0,
                pinnedShortcutCallbackIntent, FLAG_IMMUTABLE
            )

            shortcutManager.requestPinShortcut(
                pinShortcutInfo,
                successCallback.intentSender
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
