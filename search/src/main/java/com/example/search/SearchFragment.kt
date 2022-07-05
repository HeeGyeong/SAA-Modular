package com.example.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.search.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private var viewModel: SearchViewModel? = null
    private lateinit var searchAdapter: SearchAdapter

    companion object {
        private lateinit var searchFragment: SearchFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        searchFragment = this
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        viewModel = getViewModel()
        // lifecycle 설정해주지 않으면 liveData 사용 불가능
        _binding!!.lifecycleOwner = this.viewLifecycleOwner
        // DataBinding
        _binding!!.vm = viewModel

        initViewModelCallback()
        initClickEvent()
        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter { movie ->
            Intent(Intent.ACTION_VIEW, Uri.parse(movie.link)).run(this::startActivity)
        }
        binding!!.rvMovies.adapter = searchAdapter
    }

    private fun initClickEvent() {
        binding!!.moveBtn.setOnClickListener {
            findNavController().navigate(com.example.navigation.R.id.mainFragment)
        }
    }

    private fun initViewModelCallback() {
        with(viewModel!!) {
            // toastMsg 가 변경 시, 변경된 text 로 toast 를 띄워준다.
            toastMsg.observe(
                requireActivity(),
                Observer {
                    when (toastMsg.value) {
                        SearchViewModel.MessageSet.LAST_PAGE -> showToast("마지막 페이지 입니다")
                        SearchViewModel.MessageSet.EMPTY_QUERY -> showToast("검색어를 입력해주세요.")
                        SearchViewModel.MessageSet.NETWORK_NOT_CONNECTED -> showToast("네트워크를 연결해주세요.")
                        SearchViewModel.MessageSet.SUCCESS -> showToast("영화를 불러왔습니다.")
                        SearchViewModel.MessageSet.NO_RESULT -> showToast("해당 영화는 존재하지 않습니다.")
                        SearchViewModel.MessageSet.ERROR -> showToast("에러")
                        SearchViewModel.MessageSet.LOCAL_SUCCESS -> showToast("Local DB에서 불러왔습니다.")
                    }
                }
            )
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}
