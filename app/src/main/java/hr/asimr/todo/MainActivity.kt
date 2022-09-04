package hr.asimr.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import hr.asimr.todo.adapters.TaskAdapter
import hr.asimr.todo.databinding.ActivityMainBinding
import hr.asimr.todo.databinding.DialogAddTaskBinding
import hr.asimr.todo.models.Task
import hr.asimr.todo.models.TaskList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksAdapter: TaskAdapter
    private lateinit var taskList: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initTasksRecycler()
        initListeners()
    }

    private fun initList() {
        taskList = TaskList(this)
    }

    private fun initTasksRecycler() {
        tasksAdapter = TaskAdapter(
            taskList,
            onClickChangeTaskDoneStatus = { task -> task.done = !task.done },
            onLongClickDelete = { task -> taskList.remove(task) }
        )
        binding.rvTasks.adapter = tasksAdapter

        binding.rvTasks.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onResume() {
        super.onResume()
        if (taskList.isEmpty()){
            taskList.readFromFile()
        }
    }

    override fun onPause() {
        super.onPause()
        taskList.saveInFile()
    }

    private fun initListeners() {
        initButtonListeners()
    }

    private fun initButtonListeners() {
        binding.btnAdd.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheetBinding = DialogAddTaskBinding.inflate(layoutInflater)

            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.btnAddTaskDetails.setOnClickListener {
                if (bottomSheetBinding.etTask.text.toString().isNotBlank()){
                    tasksAdapter.addTask(Task(bottomSheetBinding.etTask.text.toString().trim(), false))
                    bottomSheetBinding.etTask.text?.clear()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}