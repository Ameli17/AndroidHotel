package ameli.at.hotelservice.models;

/**
 * Created by ameliatk on 03.04.17.
 */

public class UserInfo {
    protected String _id;
    protected String fullName;
    protected String login;
    protected String password;
    protected boolean isWorker;
    protected String room;
    protected String tooken;

    public boolean isWorker(){
        return isWorker;
    }

    public String getToken() {
        return tooken;
    }

    public String getID() {
        return _id;
    }
}
