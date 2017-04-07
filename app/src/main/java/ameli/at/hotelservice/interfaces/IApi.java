package ameli.at.hotelservice.interfaces;

import java.util.List;

import ameli.at.hotelservice.models.TaskModel;
import ameli.at.hotelservice.models.TaskTypeModel;
import ameli.at.hotelservice.models.UserInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ameliatk on 03.04.17.
 */

public interface IApi {

    @FormUrlEncoded
    @POST("createUser/")
    Call<UserInfo> createNewUser(@Field("fullName") String fullName,
                                 @Field("login") String login,
                                 @Field("password") String password,
                                 @Field("isWorker") boolean isWorker,
                                 @Field("room") String room);


    @FormUrlEncoded
    @POST("login/")
    Call<UserInfo> loginUser(@Field("login") String login,
                             @Field("pass") String pass);


    @GET("getAllWorkType/")
    Call<List<TaskTypeModel>> loadTaskType();


    @FormUrlEncoded
    @POST("linkTaskTypeForUser/")
    Call<UserInfo> addSupportedTasks(@Field("tooken") String tooken,
                                     @Field("id") String id,
                                     @Field("types") String types);

    @FormUrlEncoded
    @POST("createNewTask/")
    Call<UserInfo> createNewTask(@Field("tooken") String tooken,
                                 @Field("id") String id,
                                 @Field("work") String work);


    @FormUrlEncoded
    @POST("getTaskForUser/")
    Call<List<TaskModel>> getTaskForUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("changeTaskType/")
    Call<UserInfo> changeTaskType(@Field("tooken") String tooken,
                          @Field("id") String id,
                          @Field("worker") String worker,
                          @Field("status") String status);
}
