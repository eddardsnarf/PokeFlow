package com.greensock.pokeflow.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.greensock.pokeflow.R
import com.greensock.pokeflow.databinding.FragmentSecondBinding
import com.greensock.pokeflow.getMainViewModeFactory
import com.greensock.pokeflow.ui.MainViewModel
import com.greensock.pokeflow.ui.model.PokeUiState

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        getMainViewModeFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is PokeUiState.Loading -> {

                }
                is PokeUiState.Succes -> {
                    binding.imageviewSecond.load(it.pokeUiModel.spriteUrl)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
