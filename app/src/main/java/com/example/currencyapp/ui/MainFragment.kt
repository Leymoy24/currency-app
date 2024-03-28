package com.example.currencyapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.App
import com.example.currencyapp.MainAdapter
import com.example.currencyapp.R
import com.example.currencyapp.databinding.FragmentMainBinding
import com.example.currencyapp.util.NetworkUtils

class MainFragment : Fragment() {

    interface ProgressBarListener { fun showProgressBar(show: Boolean) }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel
    private var progressBarListener: ProgressBarListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProgressBarListener) {
            progressBarListener = context
        } else {
            throw RuntimeException("$context must implement ProgressBarListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = activity?.application as App
        val repository = application.repository
        val factory = MainViewModel.MainViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "Отсутствует подключение к интернету!", Toast.LENGTH_SHORT).show()
        } else {
            loadData()
        }

        adapter = MainAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun loadData() {
        progressBarListener?.showProgressBar(true)

        val textLastRequest = view?.findViewById<TextView>(R.id.textLastRequest)
        val textRelevance = view?.findViewById<TextView>(R.id.textRelevance)
        textLastRequest?.visibility = View.VISIBLE
        textRelevance?.visibility = View.VISIBLE

        viewModel.getCurrency()
        viewModel.listCurrency.observe(viewLifecycleOwner) { list ->
            list.body()?.let {
                progressBarListener?.showProgressBar(false)
                adapter.setList(it)
            } ?: run {
                progressBarListener?.showProgressBar(false)
                Toast.makeText(requireContext(), "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}