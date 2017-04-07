package ameli.at.hotelservice.fragments;

import ameli.at.hotelservice.R;

import static ameli.at.hotelservice.managers.UiManager.showCreateNewTaskFragment;

/**
 * Created by ameliatk on 04.04.17.
 */

public class SimpleUserHomeFragment extends BaseFragment {
    public static SimpleUserHomeFragment getInstance() {
        SimpleUserHomeFragment result = new SimpleUserHomeFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.simple_user_home_fragment;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this.<Click>on_R(R.id.create_new_task,this::createNewTaskClick));
    }

    private void createNewTaskClick() {
        showCreateNewTaskFragment();
    }
}
