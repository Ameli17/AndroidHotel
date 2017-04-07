package ameli.at.hotelservice.fragments;

import android.widget.CompoundButton;
import android.widget.Toast;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.managers.UiManager;
import ameli.at.hotelservice.managers.UserManager;
import ameli.at.hotelservice.models.UserInfo;

/**
 * Created by ameliatk on 03.04.17.
 */

public class CreateUserFragment extends BaseFragment {
    @Override
    protected int viewID() {
        return R.layout.create_user_fragment;
    }

    public static BaseFragment getInstance() {
        BaseFragment result = new CreateUserFragment();
        return result;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this.<Click>on_R(R.id.create_btn, this::onCreateBtnClick));
    }

    private void onCreateBtnClick() {
        String fullName = withTextView(R.id.full_name_txt, view -> view.getText().toString());
        String login = withTextView(R.id.login_txt, view -> view.getText().toString());
        String password = withTextView(R.id.pass_txt, view -> view.getText().toString());
        boolean isWorker = withCheckBox(R.id.is_worker_check, CompoundButton::isChecked);
        String room = withTextView(R.id.room_txt, view -> view.getText().toString());
        progressDialog(R.id.creating_new_user);
        UserManager.createNewUser(fullName, login, password, isWorker, room, this::onUserCreate, this::onUserCreateFail);
    }

    private void onUserCreateFail() {
        hideProgressDialog();
        Toast.makeText(getContext(),R.string.can_not_create_user,Toast.LENGTH_LONG).show();
    }

    private void onUserCreate(UserInfo info) {
        hideProgressDialog();
        if(info.isWorker())
            UiManager.showSelectTaskTypeFragment();
        else
            UiManager.showSimpleUserHomeFrament();
    }
}
