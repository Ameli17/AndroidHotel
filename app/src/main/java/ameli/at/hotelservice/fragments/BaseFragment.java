package ameli.at.hotelservice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ameli.at.hotelservice.MainActivity;
import ameli.at.hotelservice.R;
import ameli.at.hotelservice.interfaces.IOnActivityResultListener;
import ameli.at.hotelservice.interfaces.IUiItemStatus;

/**
 * Created on 12.02.16.
 */
public abstract class BaseFragment extends Fragment implements IUiItemStatus, IOnActivityResultListener {
    protected boolean isAvalible;
    protected View parent;
    private Toolbar toolbar;


    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public <T extends View> T view(int id) {
        return (T) parent.findViewById(id);
    }

    @Override
    public void onResume() {
        this.isAvalible = true;
        getMainActivity().addOnResultListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        isAvalible = false;
        getMainActivity().removeOnResultListener(this);
        try {
            View view = getMainActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public boolean isGetResponse() {
        return isAvalible;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.parent = inflater.inflate(viewID(), null, false);
        setHasOptionsMenu(true);
        getInit().exec();
        return parent;
    }


    protected String getTitle() {
        return getMainActivity().getString(R.string.app_name);
    }

    protected boolean enableDriwer() {
        return true;
    }


    protected abstract int viewID();

    protected Initer getInit() {
        return new Initer();
    }

    public boolean onBackPressed() {
        return true;
    }

    protected final class Initer {
        List<Runnable> initList;

        public Initer() {
            initList = new ArrayList<>();
        }

        public Initer add(Runnable runnable) {
            initList.add(runnable);
            return this;
        }

        private void exec() {
            for (Runnable r : initList) r.run();
        }

    }

    protected class CustomListeners implements View.OnClickListener {
        public View.OnClickListener onClickListener = null;
        public Click click = null;

        @Override
        public void onClick(View v) {

        }
    }

    protected <T> T withTextView(int id,IWith<T,TextView> action){
        return this.with(id,action);
    }

    protected <T> T withCheckBox(int id,IWith<T,CheckBox> action){
        return this.with(id,action);
    }

    protected <T,E> T with(int id,IWith<T,E> action){
        return action.action((E)parent.findViewById(id));
    }

    protected <T extends ICustomListener> void on(int viewID,T listener){
        on_R(viewID,listener).run();
    }



    protected <T extends ICustomListener> Runnable on_R(int viewID,T listener){
        return () -> {
            if (listener instanceof Click) {
                view(viewID).setOnClickListener(v -> ((Click) listener).onClick());
            }
        };
    }

    protected void progressDialog(int textRes){
        getMainActivity().showProgressDialog(textRes);
    }

    protected void hideProgressDialog(){
        getMainActivity().hideProgressDialog();
    }

    protected interface Click extends ICustomListener{
        void onClick();
    }

    protected interface ICustomListener{

    }

    public interface IWith<T,E>{
        T action(E view);
    }
}