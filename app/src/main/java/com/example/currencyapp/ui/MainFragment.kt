package com.example.currencyapp.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.App
import com.example.currencyapp.App.Companion.networkConnection
import com.example.currencyapp.R
import com.example.currencyapp.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainFragment : Fragment() {

    interface ProgressBarListener { fun showProgressBar(show: Boolean) }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler()

    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    private var progressBarListener: ProgressBarListener? = null

    private lateinit var currentDate: String
    private val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss")

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


        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                loadData()
                binding.textLastRequest.visibility = View.VISIBLE
                binding.textRelevance.visibility = View.VISIBLE
                binding.textNoConnection.visibility = View.GONE
            } else {
                Toast.makeText(requireContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
                binding.textNoConnection.visibility = View.VISIBLE
            }
        }

        adapter = MainAdapter()
        binding.recyclerView.adapter = adapter

        handler.postDelayed(updateRunnable, 30 * 1000)
    }

    private fun loadData() {
        progressBarListener?.showProgressBar(true)

        viewModel.getCurrency()
        viewModel.listCurrency.observe(viewLifecycleOwner) { list ->
            list.body()?.let {
                progressBarListener?.showProgressBar(false)
                adapter.setList(it)

                currentDate = sdf.format(Date())
                binding.textLastRequest.text = "Время последнего запроса: " + currentDate
                binding.textRelevance.text = "Последние изменения на сервере: " + formatDate(list.body()?.Date.toString())
            } ?: run {
                progressBarListener?.showProgressBar(false)
                Toast.makeText(requireContext(), "Ошибка загрузки данных!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            if (networkConnection.value == true) {
                loadData()
            } else {
                Toast.makeText(requireContext(), "Отсутствует подключение к интернету!", Toast.LENGTH_SHORT).show()
            }
            handler.postDelayed(this, 30 * 1000)
        }
    }

    private fun formatDate(dateR: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date: Date = inputFormat.parse(dateR)!!
        val outputFormat = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault())
        return outputFormat.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateRunnable)
        _binding = null
    }
}