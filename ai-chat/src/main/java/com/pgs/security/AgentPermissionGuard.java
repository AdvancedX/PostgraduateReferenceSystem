package com.pgs.security;

import com.pgs.agent.AgentUserContext;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.stereotype.Component;

@Component
public class AgentPermissionGuard {
    public void require(AgentUserContext context, String requiredPermission) {
        if (!isAllowed(context, requiredPermission)) {
            throw new AgentPermissionDeniedException(requiredPermission);
        }
    }

    public boolean isAllowed(AgentUserContext context, String requiredPermission) {
        if (context == null) {
            return false;
        }
        if (requiredPermission == null || requiredPermission.trim().isEmpty()) {
            return true;
        }
        if (context.isAdministrator()) {
            return true;
        }
        WildcardPermission required = new WildcardPermission(requiredPermission);
        for (String grantedPermission : context.getPermissions()) {
            if (grantedPermission != null
                    && new WildcardPermission(grantedPermission).implies(required)) {
                return true;
            }
        }
        return false;
    }
}
