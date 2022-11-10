package com.greensock.pokeflow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.greensock.pokeflow.R
import com.greensock.pokeflow.databinding.FragmentFirstBinding
import com.greensock.pokeflow.getMainViewModeFactory
import com.greensock.pokeflow.ui.MainViewModel
import com.greensock.pokeflow.ui.model.PokeUiState

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is PokeUiState.Loading -> {

                }
                is PokeUiState.Succes -> {
                    binding.imageviewFirst.load(it.pokeUiModel.imageUrl)
                    binding.textviewFirst.text = it.pokeUiModel.type
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
