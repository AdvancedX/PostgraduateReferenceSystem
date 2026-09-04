package com.pgs.tool;

public class AgentToolNotFoundException extends RuntimeException {
    public AgentToolNotFoundException(String toolName) {
        super("未知 Agent Tool: " + toolName);
    }
}
