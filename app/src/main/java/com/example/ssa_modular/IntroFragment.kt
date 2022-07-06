package com.example.ssa_modular

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ssa_modular.databinding.FragmentIntroBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    private var viewModel: IntroViewModel? = null
    private var intentData: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("callCheck", "onCreateView 1st call")
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        // DI를 사용하지 않고 ViewModel 사용할 때 사용.
//        viewModel = ViewModelProvider(this)[IntroViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("callCheck", "onViewCreated 2nd call")
    }

    private fun permissionCheck() {
        lifecycleScope.launch {
            if (!viewModel!!.getPermission()) {
                val permissionListener: PermissionListener = object : PermissionListener {
                    override fun onPermissionGranted() {
                        Toast.makeText(
                            activity,
                            "권한 설정 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel!!.setPermission()
                        findNavController().navigate(com.example.navigation.R.id.mainFragment, intentData)
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        Toast.makeText(
                            activity,
                            "권한 거부\n$deniedPermissions",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel!!.setPermission()
                        findNavController().navigate(com.example.navigation.R.id.mainFragment, intentData)
                    }
                }

                TedPermission.create()
                    .setPermissionListener(permissionListener)
                    .setRationaleMessage("권한 설정 합시다.")
                    .setRationaleTitle("권한 설정 받는 중")
                    .setRationaleConfirmText("알겠습니다")

                    .setDeniedTitle("권한 설정을 거절.")
                    .setDeniedMessage("권한 설정 하려면 Setting 눌러서 직접 해주세요")
                    .setDeniedCloseButtonText("닫기")

                    .setGotoSettingButtonText("설정하러 가봅시다")
                    .setPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    .check()
            } else {
                delay(2000)
                findNavController().navigate(com.example.navigation.R.id.mainFragment, intentData)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("callCheck", "onActivityCreated 3rd call")
        viewModel = getViewModel()

        val insertData = viewModel!!.getIntentData()
        if (insertData != null) {
            intentData = viewModel!!.getIntentData()
        }

        permissionCheck()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
