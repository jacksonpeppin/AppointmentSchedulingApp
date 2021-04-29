package Model;

public class User {
    private int userID;
    private String userName;
    private String userPassword;

    public User(int userID, String userName, String userPassword)
    {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public boolean isValidLogin(String username, String password)
    {
        if (username.equals(userName) && password.equals(userPassword))
        {
            return true;

        }
        else
            return false;
    }


}
