package ameli.at.hotelservice.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.interfaces.IOnTaskHolderClickListener;
import ameli.at.hotelservice.models.TaskModel;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView creator;
    private final TextView worker;
    private final TextView status;
    private Context context;
    private IOnTaskHolderClickListener listener = null;

    public TaskHolder(Context context) {
        this(LayoutInflater.from(context).inflate(R.layout.task_holder, null, false));
        this.context = context;
    }

    public TaskHolder(View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.name_txt);
        this.creator = (TextView) itemView.findViewById(R.id.creater_txt);
        this.worker = (TextView) itemView.findViewById(R.id.worker_txt);
        this.status = (TextView) itemView.findViewById(R.id.status_txt);
    }

    public void setClickListener(IOnTaskHolderClickListener listener) {
        this.listener = listener;
    }

    public void update(TaskModel taskModel) {
        name.setText(context.getString(R.string.task_name, taskModel.getName()));
        creator.setText(context.getString(R.string.creator, taskModel.getCreatorName()));
        worker.setText(context.getString(R.string.worker, taskModel.getWorkerName()));
        status.setText(context.getString(R.string.status, taskModel.getStatus()));

        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(taskModel);
            }
        });
    }
}
