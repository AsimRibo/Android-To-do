package hr.asimr.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.asimr.todo.R
import hr.asimr.todo.databinding.ItemTaskBinding
import hr.asimr.todo.models.Task

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onClickChangeTaskDoneStatus: (Task) -> Unit,
    private val onLongClickDelete:  (Task) -> Boolean
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemView.setOnLongClickListener{
            onLongClickDelete(tasks[position])
            notifyItemRemoved(position)
            true
        }
    }

    fun addTask(task: Task){
        tasks.add(task)
        notifyItemInserted(tasks.lastIndex)
    }

    override fun getItemCount() = tasks.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root){
            fun bind(task: Task){
                binding.tvDetails.text = task.details
                binding.btnCompleted.setBackgroundResource(
                    if (task.done) R.drawable.done else R.drawable.not_done
                )
                binding.btnCompleted.setOnClickListener{
                    onClickChangeTaskDoneStatus(task)
                    notifyItemChanged(tasks.indexOf(task))
                }
            }
        }
}