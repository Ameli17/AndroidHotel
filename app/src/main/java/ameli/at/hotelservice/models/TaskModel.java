package ameli.at.hotelservice.models;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskModel {
    String id ;
    String taskType;
    String taskTypeID;
    String workerName;
    String workerID;
    String creatorName;
    String creatorID;
    String status;

    public String getName() {
        return taskType;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}