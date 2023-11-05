package hai.model;

import java.util.Date;

public class Notification {
    private int id;
    private String content;
    private int userFromId;
    private int userToId;
    private Date createdDate;
    private String usenametosent;
    private boolean status = false;
    // Getters and setters

    public Notification() {
    }

    public Notification(int id, String content, int userFromId, int userToId, Date createdDate, String usenametosent, boolean status) {
        this.id = id;
        this.content = content;
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.createdDate = createdDate;
        this.usenametosent = usenametosent;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsenametosent() {
        return usenametosent;
    }

    public void setUsenametosent(String usenametosent) {
        this.usenametosent = usenametosent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}