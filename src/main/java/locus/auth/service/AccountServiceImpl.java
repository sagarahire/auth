package locus.auth.service;

import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.repository.UserAccountRepository;
import locus.auth.repository.RoleRepository;
import locus.auth.service.util.Validate;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private static AccountService accountService;
    private UserAccountRepository userAccountRepository;
    private RoleRepository roleRepository;

    private AccountServiceImpl() {
        userAccountRepository = UserAccountRepository.getUserAccountRepository();
        roleRepository = RoleRepository.getRoleRepository();
    }

    public static synchronized AccountService getAccountService() {
        if (accountService==null) {
           accountService = new AccountServiceImpl();
        }
        return accountService;
    }

    @Override
    public void registerUser(UserAccount user) {
        Validate.paramNullCheck(user);
        userAccountRepository.add(user);
    }

    @Override
    public void deregisterUser(UserAccount user) {
        Validate.paramNullCheck(user);
        userAccountRepository.remove(user);
    }


    @Override
    public void addRoleToUser(UserAccount user, Role role) {
        Validate.paramNullCheck(user, role);
        if(userAccountRepository.contains(user) && roleRepository.contains(role)) {
            UserAccount userAccount = userAccountRepository.get(user);
            userAccount.getAssignedRoles().add(role);
            roleRepository.addUserToRole(user, role);
        }

    }

    @Override
    public void addRolesToUser(UserAccount user, List<Role> roles) {
        Validate.paramNullCheck(user, roles);
        roles.stream().forEach(role -> addRoleToUser(user, role));
    }

    @Override
    public void removeRolesFromUser(UserAccount user, List<Role> roles) {
        Validate.paramNullCheck(user, roles);
        if(userAccountRepository.contains(user)) {
            for (Role role : roles) {
                removeRoleFromUser(user, role);
            }
        }
    }

    private void removeRoleFromUser(UserAccount user, Role role) {
        UserAccount userAccount = userAccountRepository.get(user);
        List<Role> roles = userAccount.getAssignedRoles();
        roles.remove(role);
        userAccount.setAssignedRoles(roles);
        userAccountRepository.add(userAccount);
    }

    @Override
    public List<Role> getAssignedRoles(UserAccount user) {
        Validate.paramNullCheck(user);
        if(userAccountRepository.contains(user)) {
            return userAccountRepository.get(user).getAssignedRoles();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isRegistered(UserAccount user) {
        Validate.paramNullCheck(user);
        return userAccountRepository.contains(user);
    }
}
