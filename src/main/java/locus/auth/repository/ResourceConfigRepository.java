package locus.auth.repository;

import locus.auth.model.ResourceConfig;
import locus.auth.repository.exception.ResourceConfigNotFoundException;
import locus.auth.service.util.Validate;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfigRepository implements DataRepository<ResourceConfig> {

    private static ResourceConfigRepository resourceConfigRepository = null;
    private Map<Integer, ResourceConfig> resourceConfigMap;
    private ResourceConfigRepository() {
        resourceConfigMap = new HashMap<>();
    }

    public static synchronized ResourceConfigRepository getResourceConfigRepository() {
        if(resourceConfigRepository == null) {
           resourceConfigRepository = new ResourceConfigRepository();
        }
        return resourceConfigRepository;
    }

    public void add(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        resourceConfigMap.put(resourceConfig.getResourceId(),resourceConfig);
    }

    public void remove(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        Integer key = resourceConfig.getResourceId();
        if(resourceConfigMap.containsKey(key)) {
            resourceConfigMap.remove(key);
        }
    }

    public ResourceConfig getById(int id) {
        if(resourceConfigMap.containsKey(id))
            return resourceConfigMap.get(id);
        throw new ResourceConfigNotFoundException();
    }

    public void removeById(int resourceId) {
        if(resourceConfigMap.containsKey(resourceId)) {
            resourceConfigMap.remove(resourceId);
        }
    }

    @Override
    public boolean contains(ResourceConfig resourceConfig) {
        Validate.paramNullCheck(resourceConfig);
        return resourceConfigMap.containsKey(resourceConfig.getResourceId());
    }

    @Override
    public boolean containsById(int id) {
        return resourceConfigMap.containsKey(id);
    }

}
