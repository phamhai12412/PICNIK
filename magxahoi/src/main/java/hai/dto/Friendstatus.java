package hai.dto;

public class Friendstatus {
    private int idsend;
    private String statust;

    public Friendstatus() {
    }

    public Friendstatus(int idsend, String statust) {
        this.idsend = idsend;
        this.statust = statust;
    }

    public int getIdsend() {
        return idsend;
    }

    public void setIdsend(int idsend) {
        this.idsend = idsend;
    }

    public String getStatust() {
        return statust;
    }

    public void setStatust(String statust) {
        this.statust = statust;
    }
}
