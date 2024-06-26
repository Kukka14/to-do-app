package com.example.to_do_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_do_app.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: NoteDataBaseHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = NoteDataBaseHelper(this)

        taskId = intent.getIntExtra("note_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getNoteById(taskId)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedTask = Note(taskId, newTitle, newContent, task.status) // Pass the status from the existing task
            db.updateNote(updatedTask)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }
}
