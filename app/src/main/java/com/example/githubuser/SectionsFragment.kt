package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.databinding.FragmentSectionsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SectionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SectionsFragment : Fragment() {
    private lateinit var binding: FragmentSectionsBinding
    private lateinit var detailViewModel : DetailViewModel

    companion object {
        const val ARG_POSITION = " "
        const val ARG_USERNAME = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var position: Int? = 0
        var username: String? = null
        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            username?.let{detailViewModel.getDataFollower(it)}
            detailViewModel.dataFollowerUser.observe(viewLifecycleOwner, {setFollowData(it)})
            detailViewModel.isLoading.observe(viewLifecycleOwner,{showLoading(it)})
        } else {
            username?.let{detailViewModel.getDataFollowing(it)}
            detailViewModel.dataFollowingUser.observe(viewLifecycleOwner, {dataUser->setFollowData(dataUser)})
            detailViewModel.isLoading.observe(viewLifecycleOwner,{showLoading(it)})
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun setFollowData(dataUser: List<ItemsItem>){
        binding.apply {
            val adapter = UserAdapter(dataUser)
            binding.rvFollow.adapter = adapter
        }

    }


}