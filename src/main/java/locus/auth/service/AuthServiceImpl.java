package locus.auth.service;

import locus.auth.model.ActionType;
import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.model.client.Resource;
import locus.auth.service.util.Validate;

import java.util.List;

public class AuthServiceImpl implements AuthService {

    private static AuthService authService;
    private ResourceManager resourceManager;
    private AccountService accountService;

    private AuthServiceImpl() {
        resourceManager = ResourceManagerImpl.getResourceManager();
        accountService = AccountServiceImpl.getAccountService();
    }

    public static synchronized AuthService getAuthService() {
        if(authService==null) {
            authService = new AuthServiceImpl();
        }
        return authService;
    }

    @Override
    public boolean isAllowed(UserAccount user, int resourceId, ActionType actionType) {
        Validate.paramNullCheck(user, actionType);

        List<Role> assignedRoles = accountService.getAssignedRoles(user);
        List<Role> requiredRoles = resourceManager.getRequiredRolesByResourceId(resourceId, actionType);

        for (Role requiredRole:requiredRoles) {
            if(assignedRoles.contains(requiredRole)) {
                return true;
            }
        }

        return false;
    }
}
