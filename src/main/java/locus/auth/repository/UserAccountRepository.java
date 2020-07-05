package locus.auth.repository;


import locus.auth.model.UserAccount;
import locus.auth.repository.exception.UserAccountNotFoundException;
import locus.auth.service.util.Validate;

import java.util.HashMap;
import java.util.Map;

public class UserAccountRepository implements DataRepository<UserAccount> {

    private static UserAccountRepository userAccountRepository;
    private Map<Integer, UserAccount> userAccountMap;

    private UserAccountRepository() {
        userAccountMap = new HashMap<>();
    }

    public static synchronized UserAccountRepository getUserAccountRepository() {
        if(userAccountRepository==null) {
            userAccountRepository = new UserAccountRepository();
        }

        return userAccountRepository;
    }

    public void add(UserAccount userAccount) {
        Validate.paramNullCheck(userAccount);
        userAccountMap.put(userAccount.getId(), userAccount);
    }

    @Override
    public UserAccount getById(int id) {
        if(userAccountMap.containsKey(id)) {
            return userAccountMap.get(id);
        }
        throw new UserAccountNotFoundException();
    }

    public UserAccount get(UserAccount userAccount) {
        Validate.paramNullCheck(userAccount);
        return getById(userAccount.getId());
    }


    public void remove(UserAccount userAccount) {
        Validate.paramNullCheck(userAccount);
        removeById(userAccount.getId());
    }

    @Override
    public void removeById(int id) {
        if(userAccountMap.containsKey(id)) {
            userAccountMap.remove(id);
        }
    }

    @Override
    public boolean contains(UserAccount userAccount) {
        Validate.paramNullCheck(userAccount);
        return containsById(userAccount.getId());
    }

    @Override
    public boolean containsById(int id) {
        return userAccountMap.containsKey(id);
    }


   /* public boolean isRegistered(UserAccount userAccount) {
        return contains(userAccount);
    }*/
}
