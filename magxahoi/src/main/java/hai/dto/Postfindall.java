package hai.dto;

import hai.model.LikeComment;
import hai.model.LikePost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Postfindall {
    private int postId;
    private int usepostId;
    private String userName;
    private String userAvatar;
    private String postContent;
    private String postImageUrl;
    private int totalLikes=0;
    private int totalComments=0;
    private List<Commentbyidpost> commentbyidpostList=new ArrayList<>();
    private Date createdDate;
private boolean status;
    public Postfindall() {
    }

    public Postfindall(int postId, int usepostId, String userName, String userAvatar, String postContent, String postImageUrl, int totalLikes, int totalComments, List<Commentbyidpost> commentbyidpostList, Date createdDate, boolean status) {
        this.postId = postId;
        this.usepostId = usepostId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
        this.totalLikes = totalLikes;
        this.totalComments = totalComments;
        this.commentbyidpostList = commentbyidpostList;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Commentbyidpost> getCommentbyidpostList() {
        return commentbyidpostList;
    }

    public void setCommentbyidpostList(List<Commentbyidpost> commentbyidpostList) {
        this.commentbyidpostList = commentbyidpostList;
    }

    public int getUsepostId() {
        return usepostId;
    }

    public void setUsepostId(int usepostId) {
        this.usepostId = usepostId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }
}
