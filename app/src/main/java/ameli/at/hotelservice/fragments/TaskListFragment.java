package ameli.at.hotelservice.fragments;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.adapters.TaskListAdapter;
import ameli.at.hotelservice.managers.TaskManager;
import ameli.at.hotelservice.models.TaskModel;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskListFragment extends BaseFragment {

    private RecyclerView taskList;
    private TaskListAdapter adapter;
    private String TAG = "TaskListFragment";

    public static TaskListFragment getInstance() {
        TaskListFragment result = new TaskListFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.task_list_fragment;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this::initList);
    }

    private void initList() {
        taskList = view(R.id.task_list);
        this.adapter = new TaskListAdapter(getMainActivity());
        taskList.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        taskList.setAdapter(adapter);
        progressDialog(R.string.loading_task);
        adapter.setListener(this::onTaskClick);
        TaskManager.getTaskForUser(this::onTaskLoad, this::onTaskLoadFail);
    }

    private void onTaskClick(TaskModel taskModel) {
        Log.i(TAG, "on task clicked :: " + taskModel.getName());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getMainActivity());
        dialogBuilder.setView(LayoutInflater.from(getMainActivity()).inflate(R.layout.task_type_dialog, null));
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.findViewById(R.id.created).setOnClickListener(v -> {
            onDialogItemClick(v, taskModel);
            alertDialog.dismiss();
        });
        alertDialog.findViewById(R.id.done).setOnClickListener(v -> {
            onDialogItemClick(v, taskModel);
            alertDialog.dismiss();
        });
        alertDialog.findViewById(R.id.in_progress).setOnClickListener(v -> {
            onDialogItemClick(v, taskModel);
            alertDialog.dismiss();
        });
    }

    private void onDialogItemClick(View view, TaskModel taskModel) {
        progressDialog(R.string.changing_type);
        TaskManager.changeTaskType(taskModel.getId()
                , ((Button) view).getText().toString()
                , this::onTaskChange
                , this::onTaskChangeFail);
    }

    private void onTaskChangeFail() {
        hideProgressDialog();
        TaskManager.getTaskForUser(this::onTaskLoad, this::onTaskLoadFail);
    }

    private void onTaskChange() {
        hideProgressDialog();
        TaskManager.getTaskForUser(this::onTaskLoad, this::onTaskLoadFail);
    }

    private void onTaskLoadFail() {
        hideProgressDialog();
    }

    private void onTaskLoad(List<TaskModel> taskModels) {
        adapter.setData(taskModels);
        hideProgressDialog();
    }
}
