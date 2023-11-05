package hai.model;

public class ChatBox {
    private int id;
    private String name;
    // Getters and setters

    public ChatBox() {
    }

    public ChatBox(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}