package com.example.aibaritone.config.template;

import java.util.*;

public class CommandTemplate {
    public String version = "1.21.8";
    public String lastUpdated = "2024-01-01";
    public String description = "默认指令模板";
    public Map<String, CommandRule> rules = new LinkedHashMap<>();
    
    public static class CommandRule {
        public String pattern;
        public String template;
        public List<String> examples;
        public String description;
        
        public CommandRule() {}
        
        public CommandRule(String pattern, String template, String description, String... examples) {
            this.pattern = pattern;
            this.template = template;
            this.description = description;
            this.examples = Arrays.asList(examples);
        }
    }
    
    public static CommandTemplate createDefault() {
        CommandTemplate template = new CommandTemplate();
        
        template.rules.put("goto", new CommandRule(
            "去|前往|走到|goto",
            "#goto {x} {y} {z}",
            "前往坐标",
            "去100 64 200"
        ));
        
        template.rules.put("mine", new CommandRule(
            "挖|开采|mine",
            "#mine {ore}",
            "自动挖矿",
            "挖钻石"
        ));
        
        template.rules.put("follow", new CommandRule(
            "跟|跟随|follow",
            "#follow {player}",
            "跟随玩家",
            "跟随Steve"
        ));
        
        template.rules.put("cancel", new CommandRule(
            "停|停止|cancel",
            "#cancel",
            "停止任务",
            "停止"
        ));
        
        return template;
    }
}
