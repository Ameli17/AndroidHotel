package ameli.at.hotelservice.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.adapters.TaskTypeAdaptor;
import ameli.at.hotelservice.managers.TaskManager;
import ameli.at.hotelservice.managers.UiManager;
import ameli.at.hotelservice.managers.UserManager;
import ameli.at.hotelservice.models.TaskTypeModel;

/**
 * Created by ameliatk on 03.04.17.
 */

public class SelectWorkerTypeFragment extends BaseFragment {
    public static final String LOG = "SelectWorker";
    private RecyclerView taskTypeList;
    private TaskTypeAdaptor adapter;

    public static SelectWorkerTypeFragment getInstance() {
        SelectWorkerTypeFragment result = new SelectWorkerTypeFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.select_worker_type;
    }

    @Override
    protected Initer getInit() {
        return super.getInit()
                .add(this::initList)
                .add(this::loadTypes)
                .add(this.<Click>on_R(R.id.submit,this::onSubmitClicked));
    }

    private void onSubmitClicked() {
        Log.i(LOG,"submitting new task type for user "+adapter.getSelectedIds());
        progressDialog(R.string.updating_supporting_task_type);
        UserManager.addSupportedTaskType(adapter.getSelectedIds(),this::onTypeUpdate,this::onTypeUpdateFail);
    }

    private void onTypeUpdateFail() {
        hideProgressDialog();
        Toast.makeText(getContext(),R.string.can_not_update_supported_task_type,Toast.LENGTH_LONG).show();
    }

    private void onTypeUpdate() {
        hideProgressDialog();
        Toast.makeText(getContext(),R.string.success_update_supported_task,Toast.LENGTH_LONG).show();
        UiManager.showWorkerHomeFragment();
    }

    private void initList() {
        taskTypeList = view(R.id.task_type_list);
        this.adapter = new TaskTypeAdaptor(getMainActivity());
        taskTypeList.setLayoutManager(new LinearLayoutManager(getMainActivity()));
        taskTypeList.setAdapter(adapter);
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

}
