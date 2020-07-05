package locus.auth.service;

import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceImplTest {

    AccountService accountService;
    RoleRepository roleRepository;
    UserAccount userAccount1;
    UserAccount userAccount2;
    Role role1;
    Role role2;

    @Before
    public void init() {
        accountService = AccountServiceImpl.getAccountService();
        roleRepository = RoleRepository.getRoleRepository();

        userAccount1 = new UserAccount(1);
        userAccount2 = new UserAccount(2);
        role1 = new Role(1, "ROLE1");
        role2 = new Role(2, "ADMIN");
        accountService.registerUser(userAccount1);
        roleRepository.add(role1);
        roleRepository.add(role2);

    }

    @Test
    public void getAccountService() {
        assertNotNull(accountService);
    }

    @Test
    public void registerUser() {
        accountService.registerUser(userAccount1); //user1 registered
        assertTrue(accountService.isRegistered(userAccount1));
        assertFalse(accountService.isRegistered(userAccount2)); //user2 not registered
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerUser_ThrowsIllegalArgumentException() {
        accountService.registerUser(null); //null input passed
    }

    @Test
    public void deregisterUser() {
        accountService.registerUser(userAccount1);
        assertTrue(accountService.isRegistered(userAccount1));
        accountService.deregisterUser(userAccount1); //user1 removed
        assertFalse(accountService.isRegistered(userAccount1));
    }

    @Test
    public void addRoleToUser() {
        accountService.registerUser(userAccount1);
        accountService.addRoleToUser(userAccount1, role1); //added role1 to user1
        assertNotNull(accountService.getAssignedRoles(userAccount1)); //role assigned shouldn't be null.
        assertEquals(accountService.getAssignedRoles(userAccount1).get(0).getId(),role1.getId()); //role id should match.

        accountService.removeRolesFromUser(userAccount1, List.of(role1)); //cleanup
    }

    @Test(expected = IllegalArgumentException.class)
    public void addRoleToUser_ThrowsIllegalArgumentException() {
        accountService.addRoleToUser(null, null); //null input passed
    }

    @Test
    public void addRolesToUser() {
        accountService.registerUser(userAccount1);
        accountService.addRolesToUser(userAccount1, List.of(role1)); //added role1 in list  to user1
        assertNotNull(accountService.getAssignedRoles(userAccount1)); //role assigned shouldn't be null.
        assertEquals(accountService.getAssignedRoles(userAccount1).get(0).getId(),role1.getId()); //role id should match.

        accountService.removeRolesFromUser(userAccount1, List.of(role1)); //cleanup

    }

    @Test
    public void removeRolesFromUser() {
        accountService.registerUser(userAccount1);
        accountService.addRolesToUser(userAccount1, List.of(role1, role2)); //added role1 and role2  to user1
        assertNotNull(accountService.getAssignedRoles(userAccount1)); //role assigned shouldn't be null.
        assertEquals(accountService.getAssignedRoles(userAccount1).get(0).getId(),role1.getId()); //role id should match.

        accountService.removeRolesFromUser(userAccount1, List.of(role1)); //role1  removed from user1
        assertNotNull(accountService.getAssignedRoles(userAccount1)); //assigned roles shouldn't be null
        assertFalse(accountService.getAssignedRoles(userAccount1).isEmpty()); //assigned roles shouldn't be empty
        assertTrue(accountService.getAssignedRoles(userAccount1).size()==1); //only role2 should be present.
        assertEquals(accountService.getAssignedRoles(userAccount1).get(0).getId(),role2.getId());

    }

    @Test
    public void getAssignedRoles() {
        accountService.registerUser(userAccount1);
        accountService.addRolesToUser(userAccount1, List.of(role1, role2)); //added role1 and role2  to user1
        assertNotNull(accountService.getAssignedRoles(userAccount1)); //role assigned shouldn't be null.
        assertFalse(accountService.getAssignedRoles(userAccount1).isEmpty()); //shouldn't be empty.
        assertEquals(accountService.getAssignedRoles(userAccount1).size(),2); //role id should match.

    }

    @Test
    public void roleCannotBeAssignedIfNotRegistered() {
        Role role3 = new Role(3, "ROLE3");
        accountService.addRoleToUser(userAccount1, role3);
        assertTrue(accountService.getAssignedRoles(userAccount1).isEmpty()); //should be empty as role3 is not already registered.

        roleRepository.add(role3); //register role 3
        accountService.addRoleToUser(userAccount1, role3);
        assertFalse(accountService.getAssignedRoles(userAccount1).isEmpty()); //should have role 3 assigned as it is registered now.
        assertTrue(accountService.getAssignedRoles(userAccount1).contains(role3));

    }
}