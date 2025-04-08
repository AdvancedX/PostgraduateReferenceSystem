package com.pgs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

/**
 * DsController
 * @author senfel
 * @version 1.0
 * @date 2025/3/13 17:21
 */
@RestController
@RequestMapping("/deepSeek")
@Slf4j
public class DsController {
    @Resource
    private DsChatService dsChatService;

    /**
     * chat page
     * @param modelAndView
     * @author senfel
     * @date 2025/3/13 17:39
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping()
    public ModelAndView chat(ModelAndView modelAndView) {
        modelAndView.setViewName("chat");
        return modelAndView;
    }

    /**
     * chat
     * @param question
     * @author senfel
     * @date 2025/3/13 17:39
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody String question) {
        //TODO 默认用户ID,实际场景从token获取
        String userId = "senfel";
        return dsChatService.chat(userId, question);
    }
}
