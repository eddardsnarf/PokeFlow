package com.greensock.pokeflow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.greensock.pokeflow.R
import com.greensock.pokeflow.databinding.FragmentSecondBinding
import com.greensock.pokeflow.databinding.ItemPokeListBinding
import com.greensock.pokeflow.getMainViewModeFactory
import com.greensock.pokeflow.ui.MainViewModel
import com.greensock.pokeflow.ui.model.PokeInfoUiModel
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

        val adapter = SecondFragmentAdapter()
        binding.rvPokeList.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL, false
        )
        binding.rvPokeList.adapter = adapter

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is PokeUiState.Loading -> {

                }
                is PokeUiState.Succes -> {
                    (binding.rvPokeList.adapter as SecondFragmentAdapter).submitList(it.list)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SecondFragmentAdapter :
    ListAdapter<PokeInfoUiModel, PokemonItemViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemViewHolder {
        return PokemonItemViewHolder(
            ItemPokeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<PokeInfoUiModel>() {
    override fun areItemsTheSame(oldItem: PokeInfoUiModel, newItem: PokeInfoUiModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokeInfoUiModel, newItem: PokeInfoUiModel): Boolean {
        return oldItem == newItem
    }
}

class PokemonItemViewHolder(val binding: ItemPokeListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PokeInfoUiModel?) {
        item ?: return
        binding.imageviewSecond.load(item.spriteUrl)
    }

}
