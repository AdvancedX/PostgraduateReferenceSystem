package com.pgs.agent;

import com.pgs.common.core.domain.entity.SysUser;
import com.pgs.common.utils.ShiroUtils;
import com.pgs.security.AgentAuthenticationException;
import com.pgs.system.service.ISysMenuService;
import com.pgs.system.service.ISysRoleService;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ShiroAgentUserContextFactory implements AgentUserContextFactory {
    private final ISysMenuService menuService;
    private final ISysRoleService roleService;

    public ShiroAgentUserContextFactory(ISysMenuService menuService, ISysRoleService roleService) {
        this.menuService = menuService;
        this.roleService = roleService;
    }

    @Override
    public AgentUserContext currentUser() {
        Subject subject = ShiroUtils.getSubject();
        SysUser user = ShiroUtils.getSysUser();
        if (subject == null || user == null || user.getUserId() == null || subject.getPrincipal() == null) {
            throw new AgentAuthenticationException("当前用户未登录");
        }

        if (user.isAdmin()) {
            return new AgentUserContext(
                    user.getUserId(),
                    user.getLoginName(),
                    Collections.singleton(AgentConstants.ADMIN_ROLE),
                    Collections.singleton(AgentConstants.ALL_PERMISSION));
        }

        Set<String> roles = safeCopy(roleService.selectRoleKeys(user.getUserId()));
        Set<String> permissions = safeCopy(menuService.selectPermsByUserId(user.getUserId()));
        return new AgentUserContext(user.getUserId(), user.getLoginName(), roles, permissions);
    }

    private Set<String> safeCopy(Set<String> source) {
        return source == null ? Collections.<String>emptySet() : new HashSet<String>(source);
    }
}
