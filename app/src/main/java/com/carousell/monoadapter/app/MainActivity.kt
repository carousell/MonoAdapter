package com.carousell.monoadapter.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.carousell.monoadapter.MonoAdapter
import com.carousell.monoadapter.app.databinding.ActivityMainBinding
import com.carousell.monoadapter.app.databinding.AdapterSampleBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = createViewBindingAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        adapter.submitList(getData())
    }

    private fun getData() = MutableList(100) { index -> "#$index" }

    private fun createViewBindingAdapter() =
        MonoAdapter.create<AdapterSampleBinding, String>(AdapterSampleBinding::inflate) {
            text.text = it
        }

    private fun createNormalAdapter() =
        MonoAdapter.create<String>(R.layout.adapter_sample) { view, string ->
            view.findViewById<TextView>(R.id.text).text = string
        }
}