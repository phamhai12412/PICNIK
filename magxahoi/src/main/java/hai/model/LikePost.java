package hai.model;

public class LikePost {
    private int id;
    private int likePostOfPostId;
    private int userLikePostId;

    // Getters and setters

    public LikePost() {
    }

    public LikePost(int id, int likePostOfPostId, int userLikePostId) {
        this.id = id;
        this.likePostOfPostId = likePostOfPostId;
        this.userLikePostId = userLikePostId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikePostOfPostId() {
        return likePostOfPostId;
    }

    public void setLikePostOfPostId(int likePostOfPostId) {
        this.likePostOfPostId = likePostOfPostId;
    }

    public int getUserLikePostId() {
        return userLikePostId;
    }

    public void setUserLikePostId(int userLikePostId) {
        this.userLikePostId = userLikePostId;
    }
}