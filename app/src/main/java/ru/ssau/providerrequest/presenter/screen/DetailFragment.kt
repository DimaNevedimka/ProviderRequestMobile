package ru.ssau.providerrequest.presenter.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.providerrequest.databinding.FragmentRequestDetailBinding
import ru.ssau.providerrequest.presenter.base.BaseFragment
import ru.ssau.providerrequest.presenter.viewmodel.DetailViewModel

class DetailFragment : BaseFragment<FragmentRequestDetailBinding>() {

    private val args by navArgs<DetailFragmentArgs>()
    private val detailViewModel by viewModel<DetailViewModel>()

    override fun initBinding(inflater: LayoutInflater): FragmentRequestDetailBinding =
        FragmentRequestDetailBinding.inflate(inflater)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            requestDetail.run {
                address.text = args.request.home.address
                date.text = args.request.home.updated_at
                detail.text = args.request.comment
                name.text = args.request.client.name + args.request.client.surname
                tariff.text = args.request.tariff.name
                number.text = args.request.id.toString()
                status.text = args.request.status
            }
            exit.setOnClickListener {
                requireActivity().finish()
            }
            end.setOnClickListener {
                detailViewModel.updateRequest(
                    id = args.request.id,
                    status = "COMPLETED",
                    date_time = args.request.home.updated_at,
                    comment = args.request.comment,
                    account_id = args.request.account_id
                )
            }
            undo.setOnClickListener {
                detailViewModel.updateRequest(
                    id = args.request.id,
                    status = "CANCELED",
                    date_time = args.request.home.updated_at,
                    comment = args.request.comment,
                    account_id = args.request.account_id
                )
            }
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            coord.setOnClickListener {
                val list = separateString(args.request.home.coords)
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToMapFragment(
                        lat = list[0].toFloat(),
                        long = list[1].toFloat()
                    )
                )

            }
            number.text = "ЗАЯВКА  №${args.request.id}"
            detailViewModel.nextScreen.observe(viewLifecycleOwner) {
                if (it) findNavController().popBackStack()
            }
        }
    }

    private fun separateString(s: String): List<Double> {
        val string = s.substringAfter(",")
        val string1 = s.substringBefore(",")
        return listOf(string1.toDouble(), string.toDouble())
    }
}