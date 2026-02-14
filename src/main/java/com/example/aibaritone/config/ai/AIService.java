package com.example.aibaritone.config.ai;

import com.example.aibaritone.config.ModConfig;
import com.example.aibaritone.config.template.TemplateManager;
import com.google.gson.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import okhttp3.*;
import java.util.concurrent.*;

public class AIService {
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ModConfig config;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static CompletableFuture<String> convertToCommand(String input) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        String templateCommand = TemplateManager.convertToCommand(input);
        if (templateCommand != null) {
            future.complete(templateCommand);
            return future;
        }
        
        CompletableFuture.runAsync(() -> {
            try {
                String result = callAIWithRetry(input);
                future.complete(result);
            } catch (Exception e) {
                if (config.ai.fallbackToOllama && config.ai.provider != AIConfig.AIProvider.OLLAMA) {
                    tryFallbackToOllama(input, future);
                } else {
                    future.completeExceptionally(e);
                }
            }
        });
        
        return future;
    }
    
    private static String callAIWithRetry(String input) throws Exception {
        int retries = 0;
        while (retries <= config.ai.maxRetries) {
            try {
                return callAI(input);
            } catch (Exception e) {
                retries++;
                if (retries > config.ai.maxRetries) throw e;
                Thread.sleep(1000 * retries);
            }
        }
        throw new Exception("所有重试均失败");
    }
    
    private static String callAI(String input) throws Exception {
        switch (config.ai.provider) {
            case OLLAMA: return callOllama(input);
            case DEEPSEEK: return callDeepSeek(input);
            case OPENAI: return callOpenAI(input);
            case QWEN: return callQwen(input);
            case DOUBAO: return callDoubao(input);
            case GLM: return callGLM(input);
            case KIMI: return callKimi(input);
            case WENXIN: return callWenxin(input);
            default: throw new Exception("未知AI提供商");
        }
    }
    
    private static String callOllama(String input) throws Exception {
        JsonObject request = new JsonObject();
        request.addProperty("model", config.ai.modelName);
        request.addProperty("stream", false);
        
        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", config.ai.systemPrompt + "\n\n用户输入: " + input);
        messages.add(userMessage);
        request.add("messages", messages);
        
        Request req = new Request.Builder()
            .url(config.ai.apiEndpoint)
            .post(RequestBody.create(gson.toJson(request), MediaType.parse("application/json")))
            .build();
        
        try (Response response = client.newCall(req).execute()) {
            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return json.getAsJsonObject("message").get("content").getAsString().trim();
        }
    }
    
    private static String callDeepSeek(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callOpenAI(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callOpenAICompatible(String input) throws Exception {
        JsonObject request = new JsonObject();
        request.addProperty("model", config.ai.modelName);
        
        JsonArray messages = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", config.ai.systemPrompt);
        messages.add(systemMessage);
        
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", input);
        messages.add(userMessage);
        
        request.add("messages", messages);
        request.addProperty("stream", false);
        
        Request req = new Request.Builder()
            .url(config.ai.apiEndpoint)
            .header("Authorization", "Bearer " + config.ai.apiKey)
            .header("Content-Type", "application/json")
            .post(RequestBody.create(gson.toJson(request), MediaType.parse("application/json")))
            .build();
        
        try (Response response = client.newCall(req).execute()) {
            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return json.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString().trim();
        }
    }
    
    private static String callQwen(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callDoubao(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callGLM(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callKimi(String input) throws Exception {
        return callOpenAICompatible(input);
    }
    
    private static String callWenxin(String input) throws Exception {
        String accessToken = getWenxinAccessToken();
        
        JsonObject request = new JsonObject();
        JsonArray messages = new JsonArray();
        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", config.ai.systemPrompt);
        messages.add(systemMessage);
        
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", input);
        messages.add(userMessage);
        
        request.add("messages", messages);
        request.addProperty("stream", false);
        
        Request req = new Request.Builder()
            .url(config.ai.apiEndpoint + "?access_token=" + accessToken)
            .header("Content-Type", "application/json")
            .post(RequestBody.create(gson.toJson(request), MediaType.parse("application/json")))
            .build();
        
        try (Response response = client.newCall(req).execute()) {
            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return json.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .get("message").getAsJsonObject()
                .get("content").getAsString().trim();
        }
    }
    
    private static String getWenxinAccessToken() throws Exception {
        String[] parts = config.ai.apiKey.split("\\|");
        if (parts.length != 2) {
            throw new Exception("文心一言API Key格式应为: API Key|Secret Key");
        }
        
        String url = "https://aip.baidubce.com/oauth/2.0/token" +
            "?grant_type=client_credentials" +
            "&client_id=" + parts[0] +
            "&client_secret=" + parts[1];
        
        Request req = new Request.Builder()
            .url(url)
            .post(RequestBody.create("", MediaType.parse("application/x-www-form-urlencoded")))
            .build();
        
        try (Response response = client.newCall(req).execute()) {
            JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
            return json.get("access_token").getAsString();
        }
    }
    
    private static void tryFallbackToOllama(String input, CompletableFuture<String> future) {
        try {
            AIConfig originalConfig = config.ai;
            config.ai.provider = AIConfig.AIProvider.OLLAMA;
            config.ai.applyDefaults();
            String result = callOllama(input);
            future.complete(result);
            config.ai = originalConfig;
            
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.sendMessage(
                    Text.of("§e[AI Baritone] 云端AI服务不可用，已自动降级到本地Ollama"),
                    false
                );
            }
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
    }
}
