package ameli.at.hotelservice.fragments;

import android.os.Handler;
import android.os.Looper;

import ameli.at.hotelservice.Const;
import ameli.at.hotelservice.R;
import ameli.at.hotelservice.managers.UserManager;

import static ameli.at.hotelservice.managers.UiManager.showLoginFragment;

/**
 * Created by ameliatk on 03.04.17.
 */

public class SplashFragment extends BaseFragment {
    @Override
    protected int viewID() {
        return R.layout.splash;
    }

    @Override
    protected Initer getInit() {
        return super.getInit().add(this::showNextFragment);
    }

    private void showNextFragment() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!UserManager.isUserLogin()) {
                showLoginFragment();
            }
        }, Const.SPLASH_DELEY);
    }

    public static BaseFragment getInstance() {
        SplashFragment result = new SplashFragment();
        return result;
    }
}
