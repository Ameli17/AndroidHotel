package ameli.at.hotelservice.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.adapters.TaskTypeAdaptor;
import ameli.at.hotelservice.interfaces.IOnTaskTypeClick;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskTypeSelectHolder extends RecyclerView.ViewHolder {

    private final TextView type;
    private final CheckBox taskName;
    private final TextView taskNameLabel;
    private boolean multiselectCupport;
    private IOnTaskTypeClick itemClickListener = null;

    public TaskTypeSelectHolder(Context context) {
        this(LayoutInflater.from(context).inflate(R.layout.select_task_type, null, false));
    }

    public TaskTypeSelectHolder(View itemView) {
        super(itemView);
        this.type = (TextView) itemView.findViewById(R.id.task_type);
        this.taskName = (CheckBox) itemView.findViewById(R.id.task_type_name);
        this.taskNameLabel = (TextView) itemView.findViewById(R.id.task_type_name_label);
    }

    public void update(TaskTypeAdaptor.ListItemData listItemData) {
        type.setText(listItemData.title);
        if (multiselectCupport) {
            taskName.setText(listItemData.typeName);
            taskName.setOnCheckedChangeListener(null);
            taskName.setChecked(listItemData.isSelect);
            taskName.setOnCheckedChangeListener((buttonView, isChecked) -> listItemData.isSelect = isChecked);
        } else {
            taskNameLabel.setText(listItemData.typeName);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null)
                    itemClickListener.onTaskTypeClick(listItemData.id);
            });
        }
    }

    public void setMultiselectCupport(boolean multiselectCupport) {
        this.multiselectCupport = multiselectCupport;
        taskNameLabel.setVisibility(multiselectCupport?View.INVISIBLE:View.VISIBLE);
        taskName.setVisibility(multiselectCupport?View.VISIBLE:View.INVISIBLE);
    }

    public void setClickListener(IOnTaskTypeClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
