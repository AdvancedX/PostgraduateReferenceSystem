package com.pgs.agent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AgentUserContext {
    private final Long userId;
    private final String loginName;
    private final Set<String> roles;
    private final Set<String> permissions;

    public AgentUserContext(Long userId, String loginName, Set<String> roles, Set<String> permissions) {
        this.userId = userId;
        this.loginName = loginName;
        this.roles = immutableCopy(roles);
        this.permissions = immutableCopy(permissions);
    }

    private static Set<String> immutableCopy(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<String>(values));
    }

    public Long getUserId() {
        return userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean isAdministrator() {
        return Long.valueOf(1L).equals(userId)
                || permissions.contains(AgentConstants.ALL_PERMISSION);
    }
}
