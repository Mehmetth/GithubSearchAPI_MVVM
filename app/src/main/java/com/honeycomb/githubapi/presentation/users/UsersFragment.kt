package com.honeycomb.githubapi.presentation.users

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.honeycomb.githubapi.R
import com.honeycomb.githubapi.UsersState
import com.honeycomb.githubapi.UsersViewModel
import com.honeycomb.githubapi.databinding.FragmentUsersBinding
import com.honeycomb.githubapi.domain.users.entity.UsersEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users), IRepoClick {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var usersViewModel: UsersViewModel

    private lateinit var repoAdapter : RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsersBinding.bind(view)

        usersViewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)
        setupRecyclerView()

        binding.usersFragmentEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                usersViewModel.currentUserName = s.toString()
            }
        })

        binding.searchImageView.setOnClickListener {
                if(usersViewModel.currentUserName.isEmpty()){
                    repoAdapter.updateDataList(mutableListOf())
                }
                else{
                    observe(usersViewModel.currentUserName)
                }
            observe("Mehmetth")
            repoAdapter.updateDataList(mutableListOf())
        }
    }

    override fun onStart() {
        super.onStart()

        if(usersViewModel.currentUserName.isNotEmpty()){
            observe(usersViewModel.currentUserName)
        }
    }
    private fun observe(userName : String){
        fetchProducts(userName)
        observeState()
        observeUsers()
    }
    private fun setupRecyclerView(){
        repoAdapter = RepoAdapter(requireActivity(),this,mutableListOf())

        binding.usersFragmentRecyclerView.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
    private fun fetchProducts(userName : String){
        Log.d("usersFragmentSearchView","fetchProducts $userName")
        usersViewModel.fetchAllSports(userName)
    }
    private fun observeState(){
        usersViewModel.mState
            .flowWithLifecycle (viewLifecycleOwner.lifecycle,  Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn (viewLifecycleOwner.lifecycleScope)
    }
    private fun observeUsers(){
        usersViewModel.mUsers
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { users ->
                if(users.isNotEmpty()){
//                    Log.d("observeUsers","${users.size}")
                    handleUsers(users)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun handleUsers(users: List<UsersEntity>){
        binding.usersFragmentRecyclerView.adapter?.let {
            if(it is RepoAdapter){
                repoAdapter.updateDataList(users)
            }
        }
    }
    private fun handleState(state: UsersState){
        when(state){
            is UsersState.IsLoading -> handleLoading(state.isLoading)
            is UsersState.ShowToast -> Toast.makeText(requireActivity(), state.message, Toast.LENGTH_LONG).show()
            is UsersState.Init -> Unit
        }
    }
    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.usersFragmentProgressBar.visibility = View.VISIBLE
        }else{
            binding.usersFragmentProgressBar.visibility = View.GONE
        }
    }

    override fun repoNameClicked(view: View, usersEntity: UsersEntity) {
        val action = UsersFragmentDirections.actionNavigationUsersToNavigationDetail(usersEntity)
        view.findNavController().navigate(action)
    }
}