package models;
/*
Explanation:
- In our app, we have groups that have some information.
- Group is something that we need to make it an object because it looks like an object (:
- put those information here and use them in your code.
 */

import java.util.ArrayList;

public class Group {
    private String name;
    private String type;
    int groupId;
    public ArrayList<User> members = new ArrayList<>();
    public ArrayList<Expense> expenses = new ArrayList<>();
    public Group(String name, String type, int groupId) {
        this.name = name;
        this.type = type;
        this.groupId = groupId;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }
    public boolean hasMember(User user) {
        return this.members.contains(user);
    }
}
