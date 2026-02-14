package com.example.aibaritone.config.template;

import java.util.regex.*;

public class TemplateManager {
    private static CommandTemplate currentTemplate = CommandTemplate.createDefault();
    
    public static String convertToCommand(String input) {
        for (CommandTemplate.CommandRule rule : currentTemplate.rules.values()) {
            Pattern pattern = Pattern.compile(rule.pattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            
            if (matcher.find()) {
                String result = rule.template;
                
                Pattern coordPattern = Pattern.compile("(-?\\d+)\\s*(-?\\d+)?\\s*(-?\\d+)?");
                Matcher coordMatcher = coordPattern.matcher(input);
                if (coordMatcher.find()) {
                    result = result.replace("{x}", coordMatcher.group(1));
                    result = result.replace("{y}", coordMatcher.group(2) != null ? coordMatcher.group(2) : "64");
                    result = result.replace("{z}", coordMatcher.group(3) != null ? coordMatcher.group(3) : 
                        coordMatcher.group(2) != null ? coordMatcher.group(2) : coordMatcher.group(1));
                }
                
                if (input.contains("钻石")) result = result.replace("{ore}", "diamond_ore");
                if (input.contains("铁")) result = result.replace("{ore}", "iron_ore");
                if (input.contains("金")) result = result.replace("{ore}", "gold_ore");
                if (input.contains("煤")) result = result.replace("{ore}", "coal_ore");
                
                Matcher playerMatcher = Pattern.compile("跟随(.+)|follow\\s+(.+)").matcher(input);
                if (playerMatcher.find()) {
                    String player = playerMatcher.group(1) != null ? playerMatcher.group(1) : playerMatcher.group(2);
                    result = result.replace("{player}", player.trim());
                }
                
                return result;
            }
        }
        return null;
    }
}
