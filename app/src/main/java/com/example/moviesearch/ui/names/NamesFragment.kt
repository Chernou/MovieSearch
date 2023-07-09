package com.example.moviesearch.ui.names

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.domain.NamesState
import com.example.moviesearch.domain.models.Name
import com.example.moviesearch.view_model.names.NamesViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class NamesFragment : Fragment() {

    private val viewModel: NamesViewModel by activityViewModel()
    private lateinit var editText: EditText
    private lateinit var progressBar: ProgressBar
    private val adapter = NamesAdapter()
    private lateinit var textWatcher: TextWatcher
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_names, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view.findViewById(R.id.names_edit_text)
        progressBar = view.findViewById(R.id.names_progress_bar)
        recyclerView = view.findViewById(R.id.names_recycler_view)
        viewModel.observeNamesState().observe(this) { state ->
            renderState(state)
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.onTextChanged(p0?.toString() ?: "")
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        editText.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editText.removeTextChangedListener(textWatcher)
    }

    private fun renderState(state: NamesState) {
        when (state) {
            is NamesState.Loading -> displayLoading()
            is NamesState.Content -> displaySearchResult(state.names)
            else -> showEmptySearch()
        }
    }

    private fun displaySearchResult(names: List<Name>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.names.clear()
        adapter.names.addAll(names)
        adapter.notifyDataSetChanged()
    }

    private fun displayLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun showEmptySearch() {
    }
}