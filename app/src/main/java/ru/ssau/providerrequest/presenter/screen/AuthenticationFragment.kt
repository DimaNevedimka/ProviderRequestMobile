package ru.ssau.providerrequest.presenter.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.providerrequest.databinding.FragmentAuthenticationBinding
import ru.ssau.providerrequest.presenter.base.BaseFragment
import ru.ssau.providerrequest.presenter.viewmodel.AuthenticationViewModel

class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding>() {

    private val authViewModel by viewModel<AuthenticationViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.containedButton.setOnClickListener {
            authViewModel.authenticate(
                binding.loginTextField.editText?.text.toString(),
                binding.passwordTextField.editText?.text.toString()
            )
        }
    }

    private fun setObserver() {
        authViewModel.apply {
            error.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
            nextScreen.observe(viewLifecycleOwner) {
                if (it){
                    findNavController().navigate(
                        AuthenticationFragmentDirections.actionAuthenticationFragmentToMainFragment()
                    )
                }
            }
        }
    }
}