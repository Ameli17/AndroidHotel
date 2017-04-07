package ameli.at.hotelservice.fragments;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.managers.UiManager;

/**
 * Created by ameliatk on 04.04.17.
 */

public class WorkerHomeFragment extends BaseFragment {
    public static WorkerHomeFragment getInstance() {
        WorkerHomeFragment result = new WorkerHomeFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.worker_home_fragment;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this.<Click>on_R(R.id.show_task_btn, UiManager::showTaskListFragment));
    }
}
