package ameli.at.hotelservice.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.adapters.TaskTypeAdaptor;
import ameli.at.hotelservice.managers.TaskManager;
import ameli.at.hotelservice.managers.UiManager;
import ameli.at.hotelservice.models.TaskTypeModel;

/**
 * Created by ameliatk on 04.04.17.
 */

public class CreateNewTaskFragment extends BaseFragment {
    RecyclerView list;
    private TaskTypeAdaptor adapter;
    private static final String TAG = "CreateNewTaskFragment";

    public static CreateNewTaskFragment getInstance() {
        CreateNewTaskFragment result = new CreateNewTaskFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.create_new_task_fragment;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this::initList).add(this::loadTypes);
    }

    private void loadTypes() {
        progressDialog(R.string.loaded_task_type);
        TaskManager.loadTaskTypes(this::onTypeLoad, this::onTypeLoadFail);
    }

    private void onTypeLoadFail() {
        hideProgressDialog();
    }

    private void onTypeLoad(List<TaskTypeModel> taskTypeModels) {
        hideProgressDialog();
        adapter.setData(taskTypeModels);
    }

    private void initList() {
        list = view(R.id.task_types_list);
        list.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        this.adapter = new TaskTypeAdaptor(getMainActivity(), false, this::onItemSelect);
        list.setAdapter(adapter);
    }

    private void onItemSelect(String s) {
        Log.i(TAG, "on item clicked :: " + s);
        progressDialog(R.string.creating_new_task);
        TaskManager.createNewTask(s, this::onTaskCreate, this::onTaskCreateFail);
    }

    private void onTaskCreateFail() {
        hideProgressDialog();
        UiManager.getInstance().back();
    }

    private void onTaskCreate() {
        hideProgressDialog();
        UiManager.getInstance().back();
    }
}
