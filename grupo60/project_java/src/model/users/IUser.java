package model.users;

import java.util.List;

/**
 * Interface para um User
 */
public interface IUser {
    public String getUser_id();

    public void setUser_id(String user_id);

    public String getUser_name();

    public void setUser_name(String user_name);

    public List<String> getFriends();

    public void setFriends(List <String> friends);
}
