package hai.model;

public class LikeComment {
    private int id;
    private int likeCommentOfCommentId;
    private int userLikeCommentId;

    // Getters and setters

    public LikeComment() {
    }

    public LikeComment(int id, int likeCommentOfCommentId, int userLikeCommentId) {
        this.id = id;
        this.likeCommentOfCommentId = likeCommentOfCommentId;
        this.userLikeCommentId = userLikeCommentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikeCommentOfCommentId() {
        return likeCommentOfCommentId;
    }

    public void setLikeCommentOfCommentId(int likeCommentOfCommentId) {
        this.likeCommentOfCommentId = likeCommentOfCommentId;
    }

    public int getUserLikeCommentId() {
        return userLikeCommentId;
    }

    public void setUserLikeCommentId(int userLikeCommentId) {
        this.userLikeCommentId = userLikeCommentId;
    }
}