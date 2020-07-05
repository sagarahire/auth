package locus.auth.service;

import locus.auth.model.ActionType;
import locus.auth.model.ResourceConfig;
import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AuthServiceImplTest {

    AuthService authService;
    AccountService accountService;
    ResourceManager resourceManager;
    RoleRepository roleRepository;

    Role role1;
    Role role2;

    ResourceConfig resourceConfig1;
    ResourceConfig resourceConfig2;

    UserAccount userAccount1;

    @Before
    public void init() {
        authService = AuthServiceImpl.getAuthService();
        accountService = AccountServiceImpl.getAccountService();
        resourceManager = ResourceManagerImpl.getResourceManager();
        roleRepository = RoleRepository.getRoleRepository();

        role1 = new Role(1,"ROLE_1");
        role2 = new Role(2, "ADMIN_ROLE");
        roleRepository.add(role1);
        roleRepository.add(role2);

        resourceConfig1 = new ResourceConfig(542);
        resourceManager.addResourceConfig(resourceConfig1);
        resourceManager.addRequiredRoleToResource(resourceConfig1.getResourceId(),ActionType.READ, role1);

        resourceConfig2 = new ResourceConfig(342);
        resourceManager.addResourceConfig(resourceConfig2);
        resourceManager.addRequiredRoleToResource(resourceConfig2.getResourceId(),ActionType.DELETE, role2);


        userAccount1 = new UserAccount(101);
        accountService.registerUser(userAccount1);
        accountService.addRoleToUser(userAccount1, role1);
        accountService.addRoleToUser(userAccount1, role2);


    }


    @Test
    public void getAuthService() {
        assertNotNull(authService);
    }

    @Test
    public void isAllowed() {
        //user1 has the read permission
        assertTrue(authService
                .isAllowed(userAccount1,resourceConfig1.getResourceId(), ActionType.READ));

    }

    @Test
    public void shouldNotAllow() {
        //user1 doesn't have the write or delete permission
        assertFalse(authService
                .isAllowed(userAccount1,resourceConfig1.getResourceId(), ActionType.WRITE));
        assertFalse(authService
                .isAllowed(userAccount1,resourceConfig1.getResourceId(), ActionType.DELETE));
    }

    @Test
    public void shouldNotAllowIfRoleRemoved() {
        //role1 for read operation removed from user1.
        accountService.removeRolesFromUser(userAccount1, List.of(role1));
        assertFalse(authService
                .isAllowed(userAccount1,resourceConfig1.getResourceId(), ActionType.READ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isAllowed_ShouldThrowException() {
        //user and actiontype is passed as null
        assertTrue(authService
                .isAllowed(null,resourceConfig1.getResourceId(), null));

    }
}