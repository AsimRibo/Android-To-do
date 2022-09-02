package hr.asimr.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.asimr.todo.R
import hr.asimr.todo.databinding.ItemTaskBinding
import hr.asimr.todo.models.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onClickCallback: (Task) -> Unit,
    private val onLongClickCallback:  () -> Boolean
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemView.setOnLongClickListener{
            notifyItemRemoved(position)
            onLongClickCallback()
        }
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
                    onClickCallback(task)
                    notifyItemChanged(tasks.indexOf(task))
                }
            }
        }
}