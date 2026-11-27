package models.enums;

/*
Explanation:
- In our app, groups have some types that are constants.
- In these cases, we use enums to define them and use them in our code.
- put those types here and use them in your code.
 */
public enum GroupType {
    home("Home"),
    trip("Trip"),
    zob("Zan-o-Bache"),
    other("Other");
    private final String groupType;
    GroupType(String type) {
        this.groupType = type;
    }

    public String getGroupType() {
        return groupType;
    }
}
