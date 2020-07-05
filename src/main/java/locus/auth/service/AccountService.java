package locus.auth.service;

import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.service.exception.AccountNotFoundException;

import java.util.List;

public interface AccountService {
    void registerUser(UserAccount user);
    void deregisterUser(UserAccount user);
    void addRolesToUser(UserAccount user, List<Role> roles);
    void addRoleToUser(UserAccount user, Role role);
    void removeRolesFromUser(UserAccount user, List<Role> roles);
    List<Role> getAssignedRoles(UserAccount user);
    boolean isRegistered(UserAccount user);
}
