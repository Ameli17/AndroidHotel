package ameli.at.hotelservice.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ameli.at.hotelservice.holders.TaskHolder;
import ameli.at.hotelservice.interfaces.IOnTaskHolderClickListener;
import ameli.at.hotelservice.models.TaskModel;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskHolder> {
    private List<TaskModel> models = new ArrayList<>();
    private Context context;
    private IOnTaskHolderClickListener listener = null;

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskHolder(context);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.update(models.get(position));
        holder.setClickListener(taskModel -> {
            if (listener != null) {
                listener.onTaskClick(taskModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<TaskModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void setListener(IOnTaskHolderClickListener listener) {
        this.listener = listener;
    }
}
