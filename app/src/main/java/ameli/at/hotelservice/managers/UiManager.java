package ameli.at.hotelservice.managers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Stack;

import ameli.at.hotelservice.MainActivity;
import ameli.at.hotelservice.R;
import ameli.at.hotelservice.fragments.BaseFragment;
import ameli.at.hotelservice.fragments.CreateNewTaskFragment;
import ameli.at.hotelservice.fragments.CreateUserFragment;
import ameli.at.hotelservice.fragments.LoginFragment;
import ameli.at.hotelservice.fragments.SelectWorkerTypeFragment;
import ameli.at.hotelservice.fragments.SimpleUserHomeFragment;
import ameli.at.hotelservice.fragments.SplashFragment;
import ameli.at.hotelservice.fragments.TaskListFragment;
import ameli.at.hotelservice.fragments.WorkerHomeFragment;


/**
 * Created on 12.02.16.
 */
public class UiManager {
    private static UiManager ourInstance = new UiManager();
    private MainActivity parent;
    private Stack<BaseFragment> backStack = new Stack<>();
    private boolean isLocateOnNawDraw = false;
    private int idTriangle = 0;
    private boolean isBackAllow = true;



    public static UiManager getInstance() {
        return ourInstance;
    }

    private UiManager() {
    }

    public void init(MainActivity parent) {
        this.parent = parent;
        parent.setContentView(R.layout.activity_main);
    }


    public boolean back() {
        if (!isBackAllow)
            return false;
        boolean isAllowBack = backStack.lastElement().onBackPressed();
        if (backStack.size() < 2 && isAllowBack) {
            parent.finish();
            return true;
        }
        if (isAllowBack) {
            backStack.pop();
            showFragment(backStack.pop());
        }
        return false;
    }

    protected void showFragment(BaseFragment fragment) {
        FragmentManager manager = parent.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        if (backStack.size() == 0 || !backStack.peek().getClass().getCanonicalName().equals(fragment.getClass().getCanonicalName()))
            backStack.push(fragment);
        if (!isLocateOnNawDraw && idTriangle != 0) {
            parent.findViewById(idTriangle).setVisibility(View.INVISIBLE);
        }
        ft = manager.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        if (!commitTransaction(ft)) return;
        if (manager == null) return;
        manager.executePendingTransactions();
    }


    private boolean commitTransaction(FragmentTransaction ft) {
        try {
            ft.commit();
        } catch (Exception e) {
            //todo log it
            try {
                ft.commitAllowingStateLoss();
            } catch (Exception e1) {
                //todo Log it
                return false;
            }
        }
        return true;
    }

    public static void showSplashFragment() {
        getInstance().showFragment(SplashFragment.getInstance());
    }

    public static void showLoginFragment(){
        getInstance().showFragment(LoginFragment.getInstance());
    }

    public static void showCreateUserFragment(){
        getInstance().showFragment(CreateUserFragment.getInstance());
    }

    public static void showSimpleUserHomeFrament(){
        getInstance().showFragment(SimpleUserHomeFragment.getInstance());
    }

    public static void showWorkerHomeFragment(){
        getInstance().showFragment(WorkerHomeFragment.getInstance());
    }

    public static  void showSelectTaskTypeFragment(){
        getInstance().showFragment(SelectWorkerTypeFragment.getInstance());
    }

    public static void showCreateNewTaskFragment(){
        getInstance().showFragment(CreateNewTaskFragment.getInstance());
    }

    public static void showTaskListFragment(){
        getInstance().showFragment(TaskListFragment.getInstance());
    }
}
