package hai.dto;

import java.util.Date;

public class Commenadd {
    private int id;
    private int postId;
    private int userId;
    private String content;
    private Date createdDate=new Date();
    private String userName;
    private String userAvatar;
    private int total_likes;
    private int total_cmt;

    public Commenadd() {
    }

    public Commenadd(int id, int postId, int userId, String content, Date createdDate, String userName, String userAvatar, int total_likes, int total_cmt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdDate = createdDate;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.total_likes = total_likes;
        this.total_cmt = total_cmt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public int getTotal_cmt() {
        return total_cmt;
    }

    public void setTotal_cmt(int total_cmt) {
        this.total_cmt = total_cmt;
    }
}
