package hai.model;

import java.util.Date;

public class Message {
    private int id;
    private int messageOfUserId;
    private int messageOfChatBoxId;
    private String content;
    private Date createdDate;

    // Getters and setters

    public Message() {
    }

    public Message(int id, int messageOfUserId, int messageOfChatBoxId, String content, Date createdDate) {
        this.id = id;
        this.messageOfUserId = messageOfUserId;
        this.messageOfChatBoxId = messageOfChatBoxId;
        this.content = content;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageOfUserId() {
        return messageOfUserId;
    }

    public void setMessageOfUserId(int messageOfUserId) {
        this.messageOfUserId = messageOfUserId;
    }

    public int getMessageOfChatBoxId() {
        return messageOfChatBoxId;
    }

    public void setMessageOfChatBoxId(int messageOfChatBoxId) {
        this.messageOfChatBoxId = messageOfChatBoxId;
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
}
