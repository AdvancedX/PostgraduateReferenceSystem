package com.pgs.controller;

import com.pgs.service.DsChatService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * DsController
 */
@RestController
@RequestMapping("/deepSeek")
public class DsController {
    @Resource
    private DsChatService dsChatService;

    @Resource
    private SecurityManager securityManager;

    @PostConstruct
    public void init() {
        // 在应用程序启动时绑定 SecurityManager
        ThreadContext.bind(securityManager);
    }

    /**
     * chat page
     */
    @GetMapping()
    public ModelAndView chat(ModelAndView modelAndView) {
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    /**
     * chat
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody String question) {
        // 确保在异步请求中也能访问 SecurityManager
        ThreadContext.bind(securityManager);

        String userId = "admin";
        return dsChatService.chat(userId, question);
    }
}