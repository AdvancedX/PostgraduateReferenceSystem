package com.pgs.agent;

import com.pgs.llm.LlmMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ConversationMemory {
    private final ConcurrentMap<String, List<LlmMessage>> histories =
            new ConcurrentHashMap<String, List<LlmMessage>>();

    public List<LlmMessage> load(Long userId, String conversationId) {
        List<LlmMessage> history = histories.get(key(userId, conversationId));
        if (history == null) {
            return Collections.emptyList();
        }
        synchronized (history) {
            return new ArrayList<LlmMessage>(history);
        }
    }

    public synchronized void appendTurn(
            Long userId,
            String conversationId,
            LlmMessage user,
            LlmMessage assistant) {
        String key = key(userId, conversationId);
        if (!histories.containsKey(key)
                && histories.size() >= AgentConstants.MAX_IN_MEMORY_CONVERSATIONS) {
            histories.remove(histories.keySet().iterator().next());
        }
        List<LlmMessage> history = histories.computeIfAbsent(
                key,
                ignored -> Collections.synchronizedList(new ArrayList<LlmMessage>()));
        synchronized (history) {
            history.add(user);
            history.add(assistant);
            while (history.size() > AgentConstants.MAX_HISTORY_MESSAGES) {
                history.remove(0);
            }
        }
    }

    private String key(Long userId, String conversationId) {
        return String.valueOf(userId) + ":" + conversationId;
    }
}
