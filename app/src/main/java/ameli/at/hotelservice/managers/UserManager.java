package ameli.at.hotelservice.managers;

import android.support.annotation.NonNull;
import android.util.Log;

import ameli.at.hotelservice.Const;
import ameli.at.hotelservice.interfaces.IApi;
import ameli.at.hotelservice.models.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ameliatk on 03.04.17.
 */

public class UserManager {
    private IApi api = null;
    private static UserManager instance = null;
    private static final String TAG = "UserManager";
    private UserInfo currentUser = null;

    UserInfo getCurrentUser(){
        return currentUser;
    }

    public static synchronized UserManager getInstance() {
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    private UserManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(IApi.class);
    }

    public static boolean isUserLogin() {
        return false;
    }

    public static void createNewUser(String fullName,
                                     String login,
                                     String password,
                                     boolean isWorker,
                                     String room,
                                     @NonNull IOnSelfUSerGet successListener,
                                     @NonNull IOnSelfUserGetFail failListener) {
        Log.i(TAG, "calling create user method");
        getInstance().api.createNewUser(fullName, login, password, isWorker, room).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.i(TAG, "on create new user");
                instance.currentUser = response.body();
                successListener.onSelfUserGet(response.body());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, "can not create new user", new Exception(t));
                failListener.onSelfUserGetFail();
            }
        });

    }

    public interface IOnSelfUserGetFail {
        void onSelfUserGetFail();
    }

    public interface IOnSelfUSerGet {
        void onSelfUserGet(UserInfo info);
    }

    public static void loginUser(String login, String password, IOnLoginUser onUserLogin, IOnLoginUserFail onUserLoginFail) {
        getInstance().api.loginUser(login, password).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.i(TAG, "on user success login");
                instance.currentUser = response.body();
                onUserLogin.onUserLoad(response.body());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, "on user login fail", new Exception(t));
                onUserLoginFail.onLoadUserFail();
            }
        });
    }

    public interface IOnLoginUser {
        void onUserLoad(UserInfo userInfo);
    }

    public interface IOnLoginUserFail {
        void onLoadUserFail();
    }

    public static void addSupportedTaskType(String IDs,
                                            IOnSupportedTypesSet successListener,
                                            IOnSupportedTypesSetFail failListener) {
        if (instance.currentUser == null) {
            failListener.onSupportedTypesSetFail();
            return;
        }
        getInstance().api.addSupportedTasks(
                instance.currentUser.getToken(),
                instance.currentUser.getID(),
                IDs).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.i(TAG, "succes update supported work type for current user");
                successListener.onSupportedTypesSet();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e(TAG, "Can not update supported work type for current user", new Exception(t));
            }
        });
    }

    public interface IOnSupportedTypesSet {
        void onSupportedTypesSet();
    }

    public interface IOnSupportedTypesSetFail {
        void onSupportedTypesSetFail();
    }

}
