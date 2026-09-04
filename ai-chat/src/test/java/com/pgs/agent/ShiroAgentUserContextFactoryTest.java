package com.pgs.agent;

import com.pgs.common.core.domain.entity.SysUser;
import com.pgs.system.service.ISysMenuService;
import com.pgs.system.service.ISysRoleService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShiroAgentUserContextFactoryTest {
    @AfterEach
    void clearShiroThreadContext() {
        ThreadContext.remove();
    }

    @Test
    void buildsContextFromServerSidePrincipalAndAuthorizationServices() {
        SysUser principal = new SysUser();
        principal.setUserId(7L);
        principal.setLoginName("student");
        Subject subject = mock(Subject.class);
        when(subject.getPrincipal()).thenReturn(principal);
        ThreadContext.bind(subject);

        ISysMenuService menuService = mock(ISysMenuService.class);
        ISysRoleService roleService = mock(ISysRoleService.class);
        when(menuService.selectPermsByUserId(7L))
                .thenReturn(Collections.singleton("school:schoolinfo:list"));
        when(roleService.selectRoleKeys(7L)).thenReturn(Collections.singleton("student"));

        AgentUserContext context = new ShiroAgentUserContextFactory(menuService, roleService).currentUser();

        assertEquals(7L, context.getUserId());
        assertEquals("student", context.getLoginName());
        assertTrue(context.getRoles().contains("student"));
        assertTrue(context.getPermissions().contains("school:schoolinfo:list"));
        verify(menuService).selectPermsByUserId(7L);
        verify(roleService).selectRoleKeys(7L);
    }
}
