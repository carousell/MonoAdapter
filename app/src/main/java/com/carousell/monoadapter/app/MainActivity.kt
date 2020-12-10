package com.carousell.monoadapter.app

import android.os.Bundle
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

        val adapter = createAdapter()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        adapter.submitList(getData())
    }

    private fun getData() = MutableList(100) { index -> "#$index" }

    private fun createAdapter() =
        MonoAdapter.create<AdapterSampleBinding, String> { binding, data ->
            binding.text.text = data
        }
}