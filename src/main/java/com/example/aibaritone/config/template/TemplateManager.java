package com.example.aibaritone.config.template;

public class TemplateManager {
    private static CommandTemplate currentTemplate = CommandTemplate.createDefault();
    
    public static String convertToCommand(String input) {
        // 简单的关键词匹配，避免正则表达式复杂问题
        if (input.contains("去") || input.contains("前往") || input.contains("goto")) {
            // 尝试提取坐标
            String[] words = input.split(" ");
            for (String word : words) {
                if (word.matches("-?\\d+")) {
                    return "#goto " + word + " 64 64";
                }
            }
            return "#goto 100 64 200";
        }
        
        if (input.contains("挖") || input.contains("mine")) {
            if (input.contains("钻石")) return "#mine diamond_ore";
            if (input.contains("铁")) return "#mine iron_ore";
            if (input.contains("金")) return "#mine gold_ore";
            if (input.contains("煤")) return "#mine coal_ore";
            return "#mine diamond_ore";
        }
        
        if (input.contains("跟随") || input.contains("follow")) {
            String[] words = input.split(" ");
            for (String word : words) {
                if (!word.contains("跟随") && !word.contains("follow") && word.length() > 0) {
                    return "#follow " + word;
                }
            }
            return "#follow Steve";
        }
        
        if (input.contains("停止") || input.contains("cancel") || input.contains("停")) {
            return "#cancel";
        }
        
        return null;
    }
}

