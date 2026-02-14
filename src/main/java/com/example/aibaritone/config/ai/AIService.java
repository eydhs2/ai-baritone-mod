package com.example.aibaritone.config.ai;

import com.example.aibaritone.config.ModConfig;
import com.example.aibaritone.config.template.TemplateManager;
import me.shedaniel.autoconfig.AutoConfig;
import java.util.concurrent.CompletableFuture;

public class AIService {
    private static ModConfig config;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static CompletableFuture<String> convertToCommand(String input) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        // 1. 先尝试模板匹配
        String templateCommand = TemplateManager.convertToCommand(input);
        if (templateCommand != null) {
            future.complete(templateCommand);
            return future;
        }
        
        // 2. 临时返回测试指令，避免复杂AI调用
        // 后续版本会接入真实AI
        if (input.contains("去") || input.contains("goto")) {
            future.complete("#goto 100 64 200");
        } else if (input.contains("挖") || input.contains("mine")) {
            future.complete("#mine diamond_ore");
        } else if (input.contains("跟随") || input.contains("follow")) {
            future.complete("#follow Steve");
        } else {
            future.complete("#goto 100 64 200");
        }
        
        return future;
    }
}

