package locus.auth.model;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    private final int id;
    private List<Role> assignedRoles;

    public UserAccount(int id) {
        this.id = id;
        this.assignedRoles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Role> getAssignedRoles() {
        return assignedRoles;
    }

    public void setAssignedRoles(List<Role> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }
}
