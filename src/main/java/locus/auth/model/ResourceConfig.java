package locus.auth.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceConfig {
    private final int resourceId;
    private Map<ActionType, List<Role>> actionToRolesMap;

    public ResourceConfig(int id) {
        this.resourceId = id;
        this.actionToRolesMap = new HashMap<>();
    }

    public int getResourceId() {
        return resourceId;
    }

    public Map<ActionType, List<Role>> getActionToRolesMap() {
        return actionToRolesMap;
    }

}
