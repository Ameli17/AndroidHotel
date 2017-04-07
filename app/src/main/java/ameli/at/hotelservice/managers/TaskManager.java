package ameli.at.hotelservice.managers;

import android.util.Log;

import java.util.List;

import ameli.at.hotelservice.interfaces.IApi;
import ameli.at.hotelservice.models.TaskModel;
import ameli.at.hotelservice.models.TaskTypeModel;
import ameli.at.hotelservice.models.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ameli.at.hotelservice.Const.SERVER_URL;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskManager {
    private static final String TAG = "TaskManager";
    private static TaskManager instance = null;
    private IApi api = null;


    public static TaskManager getInstance() {
        if (instance == null)
            instance = new TaskManager();
        return instance;
    }

    private TaskManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(IApi.class);
    }

    public static void loadTaskTypes(IOnAllTypesLoad successListener, IOnAllTypesLoadFail failListener) {
        getInstance().api.loadTaskType().enqueue(new Callback<List<TaskTypeModel>>() {
            @Override
            public void onResponse(Call<List<TaskTypeModel>> call, Response<List<TaskTypeModel>> response) {
                Log.i(TAG, "success load task type list");
                successListener.onTaskTypesLoad(response.body());
            }

            @Override
            public void onFailure(Call<List<TaskTypeModel>> call, Throwable t) {
                Log.e(TAG, "can not load task type list", new Exception(t));
                failListener.onTastTypeLoadFail();
            }
        });
    }

    public interface IOnAllTypesLoad {
        void onTaskTypesLoad(List<TaskTypeModel> types);
    }

    public interface IOnAllTypesLoadFail {
        void onTastTypeLoadFail();
    }

    public static void createNewTask(String id, IOnTaskCreate succesListener, IOnTaskCreateFail failListener) {
        UserInfo user = UserManager.getInstance().getCurrentUser();
        getInstance().api.createNewTask(user.getToken(), user.getID(), id).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.i(TAG, "new task with id " + id + " was created");
                succesListener.taskCreate();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.i(TAG, "can not create task with id " + id, new Exception(t));
                failListener.onTaskCreateFail();
            }
        });
    }

    public interface IOnTaskCreate {
        void taskCreate();
    }

    public interface IOnTaskCreateFail {
        void onTaskCreateFail();
    }

    public static void getTaskForUser(IOnTaskGet successListener, IOnTaskGetFail failListener) {
        getInstance().api.getTaskForUser(UserManager.getInstance().getCurrentUser().getID()).enqueue(new Callback<List<TaskModel>>() {
            @Override
            public void onResponse(Call<List<TaskModel>> call, Response<List<TaskModel>> response) {
                successListener.onTaskGet(response.body());
            }

            @Override
            public void onFailure(Call<List<TaskModel>> call, Throwable t) {
                failListener.onTaskGetFail();
            }
        });
    }

    public interface IOnTaskGet {
        void onTaskGet(List<TaskModel> tasks);
    }

    public interface IOnTaskGetFail {
        void onTaskGetFail();
    }

    public static void changeTaskType(String taskID, String status, IOnTaskTypeChange typeChange, IOnTaskTypeChangeFail failListener) {
        UserInfo user = UserManager.getInstance().getCurrentUser();
        getInstance().api.changeTaskType(user.getToken(), taskID, user.getID(), status).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.i(TAG,"success changed type");
                typeChange.onTaskTypeChange();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.i(TAG,"fail change type");
                failListener.onTaskTypeChangeFail();
            }
        });
    }

    public interface IOnTaskTypeChange {
        void onTaskTypeChange();
    }

    public interface IOnTaskTypeChangeFail {
        void onTaskTypeChangeFail();
    }
}
