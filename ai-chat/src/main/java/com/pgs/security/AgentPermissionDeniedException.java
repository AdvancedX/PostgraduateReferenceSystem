package com.pgs.security;

public class AgentPermissionDeniedException extends RuntimeException {
    public AgentPermissionDeniedException(String permission) {
        super("缺少 Agent Tool 权限: " + permission);
    }
}
