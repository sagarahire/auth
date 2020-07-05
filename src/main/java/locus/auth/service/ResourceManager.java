package locus.auth.service;

import locus.auth.model.ActionType;
import locus.auth.model.ResourceConfig;
import locus.auth.model.Role;

import java.util.List;

public interface ResourceManager {
    void addResourceConfig(ResourceConfig resourceConfig);
    void removeResourceConfig(ResourceConfig resourceConfig);
    List<Role> getRequiredRolesByResourceId(int resourceId, ActionType action);
    void addRequiredRolesToResource(int resource, ActionType actionType, List<Role> roles);
    void addRequiredRoleToResource(int resource, ActionType actionType, Role role);
    boolean isAdded(ResourceConfig resourceConfig);
}
