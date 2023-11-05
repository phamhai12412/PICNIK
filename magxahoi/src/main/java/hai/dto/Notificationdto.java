package hai.dto;

public class Notificationdto {
   private String notifi;
   private int notifiint;
private String content;
    public Notificationdto() {
    }

    public Notificationdto(String notifi, int notifiint, String content) {
        this.notifi = notifi;
        this.notifiint = notifiint;
        this.content = content;
    }

    public String getNotifi() {
        return notifi;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNotifi(String notifi) {
        this.notifi = notifi;
    }

    public int getNotifiint() {
        return notifiint;
    }

    public void setNotifiint(int notifiint) {
        this.notifiint = notifiint;
    }
}
