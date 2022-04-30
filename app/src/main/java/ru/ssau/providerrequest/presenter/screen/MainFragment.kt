package ru.ssau.providerrequest.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.providerrequest.databinding.FragmentMainBinding
import ru.ssau.providerrequest.presenter.adapter.RequestItemAdapter
import ru.ssau.providerrequest.presenter.base.BaseFragment
import ru.ssau.providerrequest.presenter.viewmodel.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mainViewModel by viewModel<MainViewModel>()

    private val adapter: RequestItemAdapter = RequestItemAdapter()

    override fun initBinding(inflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
        mainViewModel.loadAllData()
    }

    private fun setObserver() {
        mainViewModel.listRequests.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setAdapter() {
        binding.rvRequest.adapter = adapter
        binding.rvRequest.layoutManager = LinearLayoutManager(this.context)
        adapter.onClick = {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(it)
            )
        }
        binding.exit.setOnClickListener {
            findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToAuthenticationFragment()
            )
        }
    }
}