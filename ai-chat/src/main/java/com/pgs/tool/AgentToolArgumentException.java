package com.pgs.tool;

public class AgentToolArgumentException extends RuntimeException {
    public AgentToolArgumentException(String message) {
        super(message);
    }

    public AgentToolArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
