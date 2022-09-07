package hr.asimr.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateListImage()
        animateToDoHeader()
        animatePenLineImage()

        initList()
        initTasksRecycler()
        initListeners()
    }

    private fun setVisibilities() {
        if (taskList.isEmpty()) {
            binding.tvEmptyState.isVisible = true
            binding.rvTasks.isVisible = false
        } else {
            binding.tvEmptyState.isVisible = false
            binding.rvTasks.isVisible = true
        }
    }

    private fun animatePenLineImage() = with(binding.ivPenLine) {
        translationX = -1100f
        animate()
            .translationX(0f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(600)
            .setStartDelay(800)
            .start()
    }

    private fun animateToDoHeader() = with(binding.tvHeader) {
        alpha = 0f
        scaleX = 0.5f
        scaleY = 0.5f
        animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setInterpolator(OvershootInterpolator())
            .setDuration(800)
            .start()
    }

    private fun animateListImage() = with(binding.ivList) {
        translationY = -1100f
        animate()
            .translationY(0f)
            .setInterpolator(BounceInterpolator())
            .setDuration(1000)
            .setStartDelay(800)
            .start()
    }

    private fun initList() {
        taskList = TaskList(this)
    }

    private fun initTasksRecycler() {
        tasksAdapter = TaskAdapter(
            taskList,
            onClickChangeTaskDoneStatus = { task -> task.done = !task.done },
            onLongClickDelete = { task -> deleteTask(task) }
        )
        binding.rvTasks.adapter = tasksAdapter

        binding.rvTasks.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun deleteTask(task: Task) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.delete_confirmation))
            .setNegativeButton(getString(R.string.cancel), null)
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                val position = taskList.indexOf(task)
                taskList.remove(task)
                tasksAdapter.notifyItemRemoved(position)
                setVisibilities()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        if (taskList.isEmpty()) {
            taskList.readFromFile()
            setVisibilities()
        }
    }

    override fun onStop() {
        super.onStop()
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
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

            bottomSheetBinding.btnAddTaskDetails.setOnClickListener {
                if (bottomSheetBinding.etTask.text.toString().isNotBlank()) {
                    tasksAdapter.addTask(Task(bottomSheetBinding.etTask.text.toString().trim(), false))
                    setVisibilities()
                    bottomSheetBinding.etTask.text?.clear()
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}