package ameli.at.hotelservice.models;

import java.util.List;

/**
 * Created by ameliatk on 04.04.17.
 */

public class TaskTypeModel {
    private String _id = "";
    private String name = "";
    private List<TaskTypeModel> subtask;

    public String getName() {
        return name;
    }

    public String getID() {
        return _id;
    }

    public List<TaskTypeModel> getSubItems() {
        return subtask;
    }
}
