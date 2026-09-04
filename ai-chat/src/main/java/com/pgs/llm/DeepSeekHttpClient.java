package com.pgs.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.tool.ToolDefinition;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeepSeekHttpClient implements DeepSeekClient {
    private static final Logger log = LoggerFactory.getLogger(DeepSeekHttpClient.class);

    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String apiUrl;
    private final String model;

    public DeepSeekHttpClient(
            ObjectMapper objectMapper,
            @Value("${ds.key:}") String apiKey,
            @Value("${ds.url:https://api.deepseek.com/chat/completions}") String apiUrl,
            @Value("${ds.model:deepseek-chat}") String model) {
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
    }

    @Override
    public DeepSeekResponse chat(List<LlmMessage> messages, List<ToolDefinition> tools, String requestId) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new DeepSeekClientException("DeepSeek API Key 未配置");
        }

        Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("model", model);
        payload.put("messages", messages);
        payload.put("stream", false);
        if (tools != null && !tools.isEmpty()) {
            payload.put("tools", tools);
            payload.put("tool_choice", "auto");
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(10))
                .setResponseTimeout(Timeout.ofSeconds(90))
                .build();

        try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpPost request = new HttpPost(apiUrl);
            request.setHeader("Authorization", "Bearer " + apiKey);
            request.setEntity(new StringEntity(
                    objectMapper.writeValueAsString(payload),
                    ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8)));

            try (CloseableHttpResponse response = client.execute(request)) {
                int status = response.getCode();
                String body = response.getEntity() == null
                        ? ""
                        : EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                if (status < 200 || status >= 300) {
                    log.warn("DeepSeek request failed requestId={} status={}", requestId, status);
                    throw new DeepSeekClientException("DeepSeek 服务返回异常状态: " + status);
                }
                return parseResponse(body);
            }
        } catch (DeepSeekClientException e) {
            throw e;
        } catch (Exception e) {
            log.warn("DeepSeek request failed requestId={} cause={}", requestId, e.getClass().getSimpleName());
            throw new DeepSeekClientException("调用 DeepSeek 服务失败", e);
        }
    }

    private DeepSeekResponse parseResponse(String body) {
        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode message = root.path("choices").path(0).path("message");
            if (message.isMissingNode()) {
                throw new DeepSeekClientException("DeepSeek 响应缺少 message");
            }

            String content = message.path("content").isNull()
                    ? null
                    : message.path("content").asText(null);
            List<ToolCall> toolCalls = new ArrayList<ToolCall>();
            JsonNode toolCallsNode = message.path("tool_calls");
            if (toolCallsNode.isArray()) {
                for (JsonNode toolCallNode : toolCallsNode) {
                    JsonNode functionNode = toolCallNode.path("function");
                    JsonNode argumentsNode = functionNode.path("arguments");
                    String arguments = argumentsNode.isTextual()
                            ? argumentsNode.asText()
                            : objectMapper.writeValueAsString(argumentsNode);
                    FunctionCall function = new FunctionCall(functionNode.path("name").asText(), arguments);
                    toolCalls.add(new ToolCall(
                            toolCallNode.path("id").asText(),
                            toolCallNode.path("type").asText("function"),
                            function));
                }
            }
            return new DeepSeekResponse(LlmMessage.assistant(content, toolCalls.isEmpty() ? null : toolCalls));
        } catch (DeepSeekClientException e) {
            throw e;
        } catch (Exception e) {
            throw new DeepSeekClientException("无法解析 DeepSeek 响应", e);
        }
    }
}
