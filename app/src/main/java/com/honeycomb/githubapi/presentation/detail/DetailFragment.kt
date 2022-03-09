package com.honeycomb.githubapi.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.honeycomb.githubapi.R
import com.honeycomb.githubapi.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val args : DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        binding.repoNameTextview.text = args.repoDetail!!.name
        binding.ownerValueTexview.text = args.repoDetail!!.owner.login
        Glide.with(this).load(args.repoDetail!!.owner.avatar_url).into(binding.ownerImageImageview)

        binding.starsValueTexview.text = args.repoDetail!!.stargazers_count.toString()
        binding.openissuesValueTexview.text = args.repoDetail!!.open_issues_count.toString()
    }
}