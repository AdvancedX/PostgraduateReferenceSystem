package com.pgs.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * DsChatService
 * @author senfel
 * @version 1.0
 * @date 2025/3/13 17:30
 */
public interface DsChatService {

    /**
     * chat
     * @param userId
     * @param question
     * @date 2025/3/13 17:30
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    SseEmitter chat(String userId, String question);
}
