package locus.auth.service;

import locus.auth.model.ActionType;
import locus.auth.model.ResourceConfig;
import locus.auth.model.Role;
import locus.auth.repository.ResourceConfigRepository;
import locus.auth.repository.RoleRepository;
import locus.auth.service.util.Validate;

import java.util.ArrayList;
import java.util.List;

public class ResourceManagerImpl implements ResourceManager {

    private static ResourceManager resourceManager;
    private ResourceConfigRepository resourceConfigRepository;
    private RoleRepository roleRepository;

    private ResourceManagerImpl() {
        this.resourceConfigRepository = ResourceConfigRepository.getResourceConfigRepository();
        this.roleRepository = RoleRepository.getRoleRepository();
    }

    public static ResourceManager getResourceManager() {
        if(resourceManager == null) {
            resourceManager = new ResourceManagerImpl();
        }
        return resourceManager;
    }

    @Override
    public void addResourceConfig(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        resourceConfigRepository.add(resourceConfig);
    }

    @Override
    public void removeResourceConfig(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        resourceConfigRepository.remove(resourceConfig);
    }

    @Override
    public void addRequiredRolesToResource(int resource, ActionType actionType, List<Role> roles) {
        Validate.paramNullCheck(actionType, roles);
        addRolesToRepository(roles);
        ResourceConfig resourceConfig = resourceConfigRepository.getById(resource);
        List<Role> requiredRoles = resourceConfig.getActionToRolesMap()
                .getOrDefault(actionType, new ArrayList<>());
        requiredRoles.addAll(roles);
        resourceConfig.getActionToRolesMap().put(actionType, requiredRoles);
    }

    @Override
    public void addRequiredRoleToResource(int resource, ActionType actionType, Role role) {
        Validate.paramNullCheck(actionType, role);
        addRoleToRepository(role);
        ResourceConfig resourceConfig = resourceConfigRepository.getById(resource);
        List<Role> requiredRoles = resourceConfig.getActionToRolesMap()
                .getOrDefault(actionType, new ArrayList<>());
        requiredRoles.add(role);
        resourceConfig.getActionToRolesMap().put(actionType, requiredRoles);
    }

    @Override
    public boolean isAdded(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        return resourceConfigRepository.contains(resourceConfig);
    }

    private void addRoleToRepository(Role role) {
        if(!roleRepository.contains(role)) {
            roleRepository.add(role);
        }
    }

    private void addRolesToRepository(List<Role> roles) {
        for(Role role: roles) {
            addRoleToRepository(role);
        }
    }

    @Override
    public List<Role> getRequiredRolesByResourceId( int resourceId, ActionType action) {
        Validate.paramNullCheck(action);
        ResourceConfig resourceConfig = resourceConfigRepository.getById(resourceId);
        return resourceConfig.getActionToRolesMap().getOrDefault(action, new ArrayList<>());
    }


}
