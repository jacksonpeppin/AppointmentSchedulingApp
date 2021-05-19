package Model;

public class User {
    private int userID;
    private String userName;
    private String userPassword;

    /**
     * User represents a row in the users table
     * @param userID
     * @param userName
     * @param userPassword
     */
    public User(int userID, String userName, String userPassword)
    {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * checks if username and password match a username and password combination from the users table
     * @param username
     * @param password
     * @return true if match found, false if not
     */
    public boolean isValidLogin(String username, String password)
    {
        return username.equals(userName) && password.equals(userPassword);
    }


}
