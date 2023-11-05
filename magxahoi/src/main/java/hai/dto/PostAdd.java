package hai.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PostAdd {
    private int id;
    private int userId;
    private String content;
    private MultipartFile imgUrl;
    private Date createdDate = new Date();

    // Getters and setters
    private boolean status = true;

    public PostAdd() {
    }

    public PostAdd(int id, int userId, String content, MultipartFile imgUrl) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
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
}
