package com.pgs;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DsChatServiceImpl implements DsChatService {
    @Value("${ds.key}")
    private String dsKey;
    @Value("${ds.url}")
    private String dsUrl;
    // 用于保存每个用户的对话历史
    private final Map<String, List<Map<String, String>>> sessionHistory = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * chat
     * @param userId
     * @param question
     * @author senfel
     * @date 2025/3/13 17:36
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    @Override
    public SseEmitter chat(String userId, String question) {
        SseEmitter emitter = new SseEmitter(-1L);
        executorService.execute(() -> {
            try {
                // 获取当前用户的对话历史
                List<Map<String, String>> messages = sessionHistory.getOrDefault(userId, new ArrayList<>());
                // 添加用户的新问题到对话历史
                Map<String, String> userMessage = new HashMap<>();
                userMessage.put("role", "user");
                userMessage.put("content", question);
                Map<String, String> systemMessage = new HashMap<>();
                systemMessage.put("role", "system");
                systemMessage.put("content", "你是一个考研指导师，专门帮助考研学子确定专业发展、挑选研究生专业");
                messages.add(userMessage);
                messages.add(systemMessage);
                // 调用 DeepSeek API
                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    HttpPost request = new HttpPost(dsUrl);
                    request.setHeader("Content-Type", "application/json");
                    request.setHeader("Authorization", "Bearer " + dsKey);
                    Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put("model", "deepseek-chat");
                    requestMap.put("messages", messages);
                    requestMap.put("stream", true);
                    String requestBody = objectMapper.writeValueAsString(requestMap);
                    request.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
                    try (CloseableHttpResponse response = client.execute(request);
                         BufferedReader reader = new BufferedReader(
                                 new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
                        StringBuilder aiResponse = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("data: ")) {
                                System.err.println(line);
                                String jsonData = line.substring(6);
                                if ("[DONE]".equals(jsonData)) {
                                    break;
                                }
                                JsonNode node = objectMapper.readTree(jsonData);
                                String content = node.path("choices")
                                        .path(0)
                                        .path("delta")
                                        .path("content")
                                        .asText("");
                                if (!content.isEmpty()) {
                                    emitter.send(content);
                                    aiResponse.append(content); // 收集 AI 的回复
                                }
                            }
                        }

                        // 将 AI 的回复添加到对话历史
                        Map<String, String> aiMessage = new HashMap<>();
                        aiMessage.put("role", "assistant");
                        aiMessage.put("content", aiResponse.toString());
                        messages.add(aiMessage);
                        // 更新会话状态
                        sessionHistory.put(userId, messages);
                        //log.info("流式回答结束, 问题: {}", question);
                        emitter.complete();
                    }
                } catch (Exception e) {
                    //log.error("处理 DeepSeek 请求时发生错误", e);
                    emitter.completeWithError(e);
                }
            } catch (Exception e) {
                //log.error("处理 DeepSeek 请求时发生错误", e);
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

}
