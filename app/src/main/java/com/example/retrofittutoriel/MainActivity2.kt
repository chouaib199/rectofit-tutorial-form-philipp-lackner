package com.example.retrofittutoriel

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittutoriel.databinding.ActivityMain2Binding
import okio.IOException
import retrofit2.HttpException
const val Tag = "MainActivity"

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupRecyvlerView()
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try{
                RetrofitInstance.api.getTodos()
            } catch (e: IOException){
                Log.e(Tag, "IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }catch (e: HttpException){
                Log.e(Tag, "HttpException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                todoAdapter.todos = response.body()!!
            } else{
                Log.e(Tag,"Response not successful")
            }
            binding.progressBar.isVisible = false
        }

    }
    private fun setupRecyvlerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
       layoutManager = LinearLayoutManager(this@MainActivity2)
    }
}


























































