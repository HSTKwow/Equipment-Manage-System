package Model;

import java.util.Date;

public class User {
    private int userId;
    private String userName;
    private String userPassward;

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassward() {
        return userPassward;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassward(String userPassward) {
        this.userPassward = userPassward;
    }
}
