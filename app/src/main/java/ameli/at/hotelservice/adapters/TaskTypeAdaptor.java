package ameli.at.hotelservice.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ameli.at.hotelservice.holders.TaskTypeSelectHolder;
import ameli.at.hotelservice.interfaces.IOnTaskTypeClick;
import ameli.at.hotelservice.models.TaskTypeModel;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskTypeAdaptor extends RecyclerView.Adapter<TaskTypeSelectHolder> {

    private Context context;
    private boolean isMyltiselect = true;
    private List<ListItemData> items = new ArrayList<>();
    private IOnTaskTypeClick itemClickListener = null;

    public TaskTypeAdaptor(Context context) {
        this(context, true,null);
    }

    public TaskTypeAdaptor(Context context, boolean isMyltiselect,IOnTaskTypeClick itemClickListener) {
        this.context = context;
        this.isMyltiselect = isMyltiselect;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public TaskTypeSelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskTypeSelectHolder taskTypeSelectHolder = new TaskTypeSelectHolder(context);
        taskTypeSelectHolder.setMultiselectCupport(isMyltiselect);
        taskTypeSelectHolder.setClickListener(itemClickListener);
        return taskTypeSelectHolder;
    }

    @Override
    public void onBindViewHolder(TaskTypeSelectHolder holder, int position) {
        holder.update(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(@NonNull List<TaskTypeModel> items) {
        this.items.clear();
        for (TaskTypeModel category : items) {
            for (TaskTypeModel item : category.getSubItems()) {
                this.items.add(new ListItemData(item.getName(), category.getName(), item.getID()));
            }
        }
        notifyDataSetChanged();
    }

    public class ListItemData {
        public String typeName = "";
        public String title = "";
        public String id = "";
        public boolean isSelect = false;

        public ListItemData(String typeName, String title, String id) {
            this.typeName = typeName;
            this.title = title;
            this.id = id;
        }
    }

    //return string with selected ids of task type separated by ','
    public String getSelectedIds() {
        List<String> strings = new LinkedList<>();
        for (ListItemData item : items) {
            if (item.isSelect)
                strings.add(item.id);
        }
        if (strings.size() == 0)
            return "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            result.append(strings.get(i));
            if (i < strings.size() - 1)
                result.append(",");
        }
        return result.toString();
    }
}
