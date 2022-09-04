package hr.asimr.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import hr.asimr.todo.databinding.ActivityMainBinding
import hr.asimr.todo.databinding.DialogAddTaskBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        initButtonListeners()
    }

    private fun initButtonListeners() {
        binding.btnAdd.setOnClickListener{
            val dialog = BottomSheetDialog(this)
            val bottomSheetBinding = DialogAddTaskBinding.inflate(layoutInflater)

            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.btnAddTaskDetails.setOnClickListener{
                bottomSheetBinding.etTask.text?.clear()
            }

            dialog.show()
        }
    }
}