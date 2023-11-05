package hai.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String avatar="male.png";
    private String phone;

    private Date Birthdate= new Date();
    private boolean status=true;
    private boolean role=false;
private List<Integer> listidpostlike =new ArrayList<>();
private List<Integer> listidcommentlike =new ArrayList<>();
private int view;

    public User(int id, String name, String email, String password, String avatar, String phone, Date birthdate, boolean status, boolean role, List<Integer> listidpostlike, List<Integer> listidcommentlike, int view) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        Birthdate = birthdate;
        this.status = status;
        this.role = role;
        this.listidpostlike = listidpostlike;
        this.listidcommentlike = listidcommentlike;
        this.view = view;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public User() {
    }

    public User(int id, String name, String email, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public User(int id, String name, String email, String password, String avatar, boolean status, boolean role, List<Integer> listidpostlike, List<Integer> listidcommentlike) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.status = status;
        this.role = role;
        this.listidpostlike = listidpostlike;
        this.listidcommentlike = listidcommentlike;
    }

    public User(int id, String name, String email, String password, String avatar, String phone, Date birthdate, boolean status, boolean role, List<Integer> listidpostlike, List<Integer> listidcommentlike) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        Birthdate = birthdate;
        this.status = status;
        this.role = role;
        this.listidpostlike = listidpostlike;
        this.listidcommentlike = listidcommentlike;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

    public List<Integer> getListidpostlike() {
        return listidpostlike;
    }

    public void setListidpostlike(List<Integer> listidpostlike) {
        this.listidpostlike = listidpostlike;
    }

    public List<Integer> getListidcommentlike() {
        return listidcommentlike;
    }

    public void setListidcommentlike(List<Integer> listidcommentlike) {
        this.listidcommentlike = listidcommentlike;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
    // Getters and setters
}