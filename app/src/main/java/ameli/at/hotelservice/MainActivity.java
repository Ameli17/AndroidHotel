package ameli.at.hotelservice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ameli.at.hotelservice.interfaces.IOnActivityResultListener;
import ameli.at.hotelservice.interfaces.IOnResumeListener;
import ameli.at.hotelservice.interfaces.IOnstartListener;
import ameli.at.hotelservice.managers.UiManager;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private boolean isStarted = false;

    private List<IOnActivityResultListener> resultListeners = new ArrayList<>();
    private Set<IOnResumeListener> resumeListeners = new HashSet<>();
    private Set<IOnstartListener> startListeners = new HashSet<>();

    protected ProgressDialog progressDialog = null;

    public void addResumeListener(IOnResumeListener resumeListener) {
        resumeListeners.add(resumeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (IOnResumeListener r : resumeListeners)
            r.onActivityResume();
    }

    public void addOnStartListener(IOnstartListener listener) {
        startListeners.add(listener);
    }

    public void addOnResultListener(IOnActivityResultListener listener) {
        for (IOnActivityResultListener l : resultListeners)
            if (l.equals(listener))
                return;
        resultListeners.add(listener);
    }

    public void removeOnResultListener(IOnActivityResultListener listener) {
        for (int i = 0; i < resultListeners.size(); i++) {
            if (resultListeners.get(i).equals(listener)) {
                resultListeners.remove(i);
                i--;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UiManager.getInstance().init(this);
        UiManager.showSplashFragment();
    }


    @Override
    public void onBackPressed() {
        if (UiManager.getInstance().back())
            super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        isStarted = true;
        for (IOnstartListener l : startListeners)
            l.onActivityStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStarted = false;
        hideProgressDialog();
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void showProgressDialog(int textResId) {
        showProgressDialog(getString(textResId));
    }
    public void showProgressDialog(String text) {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public void hideProgressDialog(){
        if(progressDialog == null)
            return;
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

}