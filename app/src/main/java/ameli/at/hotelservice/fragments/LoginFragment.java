package ameli.at.hotelservice.fragments;

import ameli.at.hotelservice.R;
import ameli.at.hotelservice.managers.UiManager;
import ameli.at.hotelservice.managers.UserManager;
import ameli.at.hotelservice.models.UserInfo;

/**
 * Created by ameliatk on 03.04.17.
 */

public class LoginFragment extends BaseFragment {

    public static BaseFragment getInstance() {
        BaseFragment result = new LoginFragment();
        return result;
    }

    @Override
    protected int viewID() {
        return R.layout.login_fragment;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this::initCreateUserBtn).add(this.<Click>on_R(R.id.login, this::onLoginBtnClick));
    }

    private void onLoginBtnClick() {
        String login = withTextView(R.id.login_txt, view -> view.getText().toString());
        String pass = withTextView(R.id.pass_txt, view -> view.getText().toString());
        progressDialog(R.string.logining_user);
        UserManager.loginUser(login, pass, this::onLogin, this::onLoginFail);
    }


    private void onLoginFail() {

    }

    private void onLogin(UserInfo userInfo) {
        hideProgressDialog();
        if (userInfo.isWorker())
            UiManager.showWorkerHomeFragment();
        else UiManager.showSimpleUserHomeFrament();
    }

    private void initCreateUserBtn() {
        hideProgressDialog();
        this.<Click>on(R.id.registration, UiManager::showCreateUserFragment);
    }
}
