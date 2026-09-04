package com.pgs.security;

import com.pgs.agent.AgentUserContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgentPermissionGuardTest {
    private final AgentPermissionGuard guard = new AgentPermissionGuard();

    @Test
    void allowsExactPermission() {
        AgentUserContext context = context(2L, "school:schoolinfo:list");
        assertDoesNotThrow(() -> guard.require(context, "school:schoolinfo:list"));
    }

    @Test
    void deniesMissingPermission() {
        AgentUserContext context = context(2L, "major:majorinfo:list");
        assertThrows(
                AgentPermissionDeniedException.class,
                () -> guard.require(context, "school:schoolinfo:list"));
    }

    @Test
    void followsShiroWildcardAndAdministratorSemantics() {
        assertTrue(guard.isAllowed(context(2L, "school:*:*"), "school:schoolinfo:list"));
        assertTrue(guard.isAllowed(context(2L, "*:*:*"), "score:score:list"));
        assertTrue(guard.isAllowed(context(1L, null), "major:majorinfo:list"));
        assertFalse(guard.isAllowed(context(2L, null), "score:score:list"));
        AgentUserContext roleNamedAdmin = new AgentUserContext(
                2L,
                "tester",
                Collections.singleton("admin"),
                Collections.<String>emptySet());
        assertFalse(guard.isAllowed(roleNamedAdmin, "score:score:list"));
    }

    private AgentUserContext context(Long userId, String permission) {
        return new AgentUserContext(
                userId,
                "tester",
                Collections.<String>emptySet(),
                permission == null
                        ? Collections.<String>emptySet()
                        : Collections.singleton(permission));
    }
}
