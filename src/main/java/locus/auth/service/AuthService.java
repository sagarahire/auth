package locus.auth.service;

import locus.auth.model.ActionType;
import locus.auth.model.UserAccount;
import locus.auth.model.client.Resource;

public interface AuthService {
    boolean isAllowed(UserAccount user, int resourceId, ActionType actionType);
}
