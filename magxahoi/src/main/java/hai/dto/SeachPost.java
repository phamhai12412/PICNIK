package hai.dto;

public class SeachPost {
    String searchName;
    String sort="like";
    String by="ASC";

    public SeachPost() {
    }

    public SeachPost(String searchName, String sort, String by) {
        this.searchName = searchName;
        this.sort = sort;
        this.by = by;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }
}
