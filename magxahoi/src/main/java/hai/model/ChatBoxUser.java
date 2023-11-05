package hai.model;

public class ChatBoxUser {
    private int id;
    private int chatBoxId;
    private int chatBoxOfUserId;

    // Getters and setters

    public ChatBoxUser() {
    }

    public ChatBoxUser(int id, int chatBoxId, int chatBoxOfUserId) {
        this.id = id;
        this.chatBoxId = chatBoxId;
        this.chatBoxOfUserId = chatBoxOfUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatBoxId() {
        return chatBoxId;
    }

    public void setChatBoxId(int chatBoxId) {
        this.chatBoxId = chatBoxId;
    }

    public int getChatBoxOfUserId() {
        return chatBoxOfUserId;
    }

    public void setChatBoxOfUserId(int chatBoxOfUserId) {
        this.chatBoxOfUserId = chatBoxOfUserId;
    }
}