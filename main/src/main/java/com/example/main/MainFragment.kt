package com.example.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.main.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainFragment : Fragment() {

    private var navController: NavController? = null
    private lateinit var callback: OnBackPressedCallback

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var viewModel: MainViewModel? = null

    // SubFragment에서 MainFragment의 Method를 사용하기 위하여 instance 선언
    companion object {
        private lateinit var mainFragment: MainFragment
        fun getInstance(): MainFragment = mainFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d("viewModelData", "MainFragment onCreateView Call 1")
        mainFragment = this
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        Log.d("viewModelData", "MainFragment onCreateView Call 2")

        // DataBinding 사용 시 교체 필요. 현재 구조에서는 사용하지 않으므로 주석처리.
//        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        // Use ViewModel
        // private val viewModel: MainViewModel by viewModel()
        viewModel = getViewModel()
//        _binding!!.vm = viewModel
        viewModel!!.initData()
        Log.d("viewModelData", "MainFragment Data ? ${viewModel!!.checkData}")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("viewModelData", "MainFragment onViewCreated Call 3")

        // permissionCheck 시 데이터를 던지지 않아 오류가 발생한다.
        try {
            val args: MainFragmentArgs by navArgs()
            val userData = args.userData.toCollection(ArrayList())
            Toast.makeText(context, "data : ${userData.size} ", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("userData", "Try - catch exception : ${e.message}")
        }

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController!!)

        binding.navView.setOnItemSelectedListener {
            it.onNavDestinationSelected(navController!!)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("viewModelData", "MainFragment onActivityCreated Call 4")
        initCallBack()
        val intentData = arguments?.getString("destination")

        if (intentData != null) {
            setIntentData(intentData)
        }
    }

    // SubFragment간의 이동이 아닌 ParentFragment간의 이동을 위해 사용.
    fun moveSearchFragment() {
        findNavController().navigate(com.example.navigation.R.id.searchFragment)
    }

    private fun setIntentData(intentData: String) {
        val naviIndex = when (intentData) {
            "TextFragment" -> {
                1
            }
            "MoveFragment" -> {
                2
            }
            else -> {
                0
            }
        }

        binding.navView.menu[naviIndex].onNavDestinationSelected(navController!!)
    }

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
    private fun initCallBack() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                    activity?.finish()
                    toast!!.cancel()
                } else {
                    backKeyPressedTime = System.currentTimeMillis()
                    showGuide()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun showGuide() {
        toast =
            Toast.makeText(activity, "한번 더 누르시면 앱을 종료합니다.", Toast.LENGTH_SHORT)
        toast!!.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callback.remove()
    }
}
