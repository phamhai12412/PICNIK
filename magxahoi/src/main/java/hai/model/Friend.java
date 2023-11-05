package hai.model;

public class Friend {
    private int id;
    private int userOnlineId;
    private int userFriendId;
    private boolean status;

    // Getters and setters

    public Friend() {
    }

    public Friend(int id, int userOnlineId, int userFriendId, boolean status) {
        this.id = id;
        this.userOnlineId = userOnlineId;
        this.userFriendId = userFriendId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserOnlineId() {
        return userOnlineId;
    }

    public void setUserOnlineId(int userOnlineId) {
        this.userOnlineId = userOnlineId;
    }

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}