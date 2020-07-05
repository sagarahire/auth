package locus.auth.repository;

import locus.auth.model.Role;
import locus.auth.model.UserAccount;
import locus.auth.repository.exception.RoleNotFoundException;
import locus.auth.service.util.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleRepository implements DataRepository<Role> {

    private Map<Integer, Role> roleMap;
    private Map<Integer, List<Integer>> roleIdToUserIdsMap;
    private static RoleRepository roleRepository;

    private RoleRepository() {
        this.roleMap = new HashMap<>();
        this.roleIdToUserIdsMap = new HashMap<>();
    }

    public static RoleRepository getRoleRepository() {
        if(roleRepository==null) {
            roleRepository = new RoleRepository();
        }
        return roleRepository;
    }

    public void add(Role role) {
        Validate.paramNullCheck(role);
        roleMap.put(role.getId(), role);
    }

    public void addUserToRole(UserAccount user, Role role) {
        Validate.paramNullCheck(user, role);
        List<Integer> userIds = roleIdToUserIdsMap.getOrDefault(role.getId(), new ArrayList());
        userIds.add(user.getId());
        roleIdToUserIdsMap.put(role.getId(), userIds);
    }

    public Role getById(int id) {
        if(roleMap.containsKey(id)) {
            roleMap.get(id);
        }

        throw new RoleNotFoundException();
    }

    @Override
    public void remove(Role role) {
        Validate.paramNullCheck(role);
        removeById(role.getId());
    }

    @Override
    public void removeById(int id) {
        if(roleMap.containsKey(id)) {
            roleMap.remove(id);
        }
    }

    public List<Integer> getUserIdsByRole(Role role) {
        Validate.paramNullCheck(role);
        if(roleIdToUserIdsMap.containsKey(role.getId())) {
            roleIdToUserIdsMap.get(role.getId());
        }

        return new ArrayList<>();
    }

    @Override
    public boolean contains(Role role) {
        Validate.paramNullCheck(role);
        return roleMap.containsKey(role.getId());
    }

    @Override
    public boolean containsById(int id) {
        return roleMap.containsKey(id);
    }
}
