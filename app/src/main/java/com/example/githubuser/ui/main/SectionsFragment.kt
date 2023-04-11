package com.example.githubuser.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.databinding.FragmentSectionsBinding
import com.example.githubuser.networking.response.ItemsItem
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.viewmodel.DetailViewModel

class SectionsFragment : Fragment() {
    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel : DetailViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var position: Int? = 0
        var username: String? = null
        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            username?.let{detailViewModel.getDataFollower(it)}
            detailViewModel.dataFollowerUser.observe(viewLifecycleOwner, {setFollowData(it)})
            detailViewModel.isLoadingFollower.observe(viewLifecycleOwner,{showLoading(it)})
        } else {
            username?.let{detailViewModel.getDataFollowing(it)}
            detailViewModel.dataFollowingUser.observe(viewLifecycleOwner, {dataUser->setFollowData(dataUser)})
            detailViewModel.isLoadingFollowing.observe(viewLifecycleOwner,{showLoading(it)})
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }

    private fun setFollowData(dataUser: List<ItemsItem>){
        binding.apply {
            val adapter = UserAdapter(dataUser)
            binding?.rvFollow?.adapter = adapter
        }

    }

    companion object {
        const val ARG_POSITION = " "
        const val ARG_USERNAME = ""
    }


}