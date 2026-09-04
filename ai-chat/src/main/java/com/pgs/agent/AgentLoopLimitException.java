package com.pgs.agent;

public class AgentLoopLimitException extends RuntimeException {
    public AgentLoopLimitException(int maxRounds) {
        super("Agent Tool 调用超过最大轮次: " + maxRounds);
    }
}
