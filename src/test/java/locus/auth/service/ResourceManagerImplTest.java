package locus.auth.service;

import com.sun.source.tree.AssertTree;
import locus.auth.model.ActionType;
import locus.auth.model.ResourceConfig;
import locus.auth.model.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ResourceManagerImplTest {

    ResourceManager resourceManager;
    ResourceConfig rc1;
    ResourceConfig rc2;
    Role r1;
    Role r2;

    @Before
    public void setUp() throws Exception {
        resourceManager = ResourceManagerImpl.getResourceManager();
        rc1 = new ResourceConfig(311);
        r1 = new Role(1, "R1");
        r2 = new Role(2, "R2");
    }

    @Test
    public void getResourceManager() {
        assertNotNull(resourceManager);
    }

    @Test
    public void addResourceConfig() {
        assertFalse(resourceManager.isAdded(rc1)); // should be false as resource not added yet.
        resourceManager.addResourceConfig(rc1); //resource config added
        assertTrue(resourceManager.isAdded(rc1)); // should be true

    }

    @Test(expected = IllegalArgumentException.class)
    public void addResourceConfig_ThrowsIllegalArgumentException() {
        resourceManager.addResourceConfig(null); //null input passed

    }

    @Test
    public void removeResourceConfig() {
        resourceManager.addResourceConfig(rc1); //added rsource config
        assertTrue(resourceManager.isAdded(rc1));

        resourceManager.removeResourceConfig(rc1);
        assertFalse(resourceManager.isAdded(rc1)); //should be false after removal
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeResourceConfig_ThrowsIllegalArgumentException() {
        resourceManager.removeResourceConfig(null); //null input passed
    }

    @Test
    public void addRequiredRolesToResource() {
        resourceManager.addResourceConfig(rc1);
        assertTrue(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE)
                    .isEmpty()); //role not added, hence empty.
        resourceManager.addRequiredRolesToResource(rc1.getResourceId(), ActionType.WRITE, List.of(r1));
        assertFalse(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE)
                .isEmpty()); //role added
        assertEquals(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE).get(0).getId()
                ,r1.getId());
    }

    @Test
    public void addRequiredRoleToResource() {
        resourceManager.addResourceConfig(rc1);
        assertTrue(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE)
                .isEmpty()); //role not added, hence empty.
        resourceManager.addRequiredRoleToResource(rc1.getResourceId(), ActionType.WRITE, r2);
        assertFalse(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE)
                .isEmpty()); //role added
        assertEquals(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE).get(0).getId()
                ,r2.getId());

    }

    @Test
    public void getRequiredRolesByResourceId() {
        resourceManager.addResourceConfig(rc1);
        resourceManager.addRequiredRoleToResource(rc1.getResourceId(), ActionType.WRITE, r2); //role added
        assertFalse(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE)
                .isEmpty()); //role shouldn't be empty.
        assertEquals(resourceManager.getRequiredRolesByResourceId(rc1.getResourceId(), ActionType.WRITE).get(0).getId()
                ,r2.getId()); //added role id should match
    }
}