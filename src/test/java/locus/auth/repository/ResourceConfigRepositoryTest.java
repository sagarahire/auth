package locus.auth.repository;

import locus.auth.model.ResourceConfig;
import locus.auth.model.UserAccount;
import locus.auth.repository.exception.ResourceConfigNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceConfigRepositoryTest {

    ResourceConfigRepository resourceConfigRepository;

    @Before
    public void init() {
        resourceConfigRepository = ResourceConfigRepository.getResourceConfigRepository();
    }

    @Test
    public void getResourceConfigRepository() {
        assertNotNull(resourceConfigRepository);
    }

    @Test
    public void add() {
        ResourceConfig resourceConfig = new ResourceConfig(123);
        resourceConfigRepository.add(resourceConfig);
        assertTrue(resourceConfigRepository.getById(resourceConfig.getResourceId()).getResourceId()==resourceConfig.getResourceId());
    }

    @Test(expected = ResourceConfigNotFoundException.class )
    public void remove() {
        ResourceConfig resourceConfig = new ResourceConfig(875);
        resourceConfigRepository.remove(resourceConfig);
        resourceConfigRepository.getById(resourceConfig.getResourceId());

    }

    @Test
    public void getById(){
        ResourceConfig resourceConfig = new ResourceConfig(931);
        resourceConfigRepository.add(resourceConfig);
        assertNotNull(resourceConfigRepository.getById(931));
    }

    @Test(expected = ResourceConfigNotFoundException.class )
    public void getById_WhenResourceConfigNotPresent(){
       resourceConfigRepository.getById(4121);
    }

    @Test(expected = ResourceConfigNotFoundException.class )
    public void removeById() {
        ResourceConfig resourceConfig = new ResourceConfig(121);
        resourceConfigRepository.add(resourceConfig);
        resourceConfigRepository.removeById(121);
        resourceConfigRepository.getById(121);
    }
}