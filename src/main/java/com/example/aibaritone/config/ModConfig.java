package com.example.aibaritone.config;

import com.example.aibaritone.config.ai.AIConfig;
import com.example.aibaritone.config.status.PlayerStatusConfig;
import com.example.aibaritone.config.status.PlayerControlConfig;
import com.example.aibaritone.config.template.CommandTemplate;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "ai_baritone")
public class ModConfig implements ConfigData {
    
    public boolean enableAICompletion = true;
    public int serverPort = 12345;
    public boolean debugMode = false;
    
    @ConfigEntry.Gui.CollapsibleObject
    public AIConfig ai = new AIConfig();
    
    @ConfigEntry.Gui.CollapsibleObject
    public CommandTemplateSettings commands = new CommandTemplateSettings();
    
    @ConfigEntry.Gui.CollapsibleObject
    public PlayerStatusConfig playerStatus = new PlayerStatusConfig();
    
    @ConfigEntry.Gui.CollapsibleObject
    public PlayerControlConfig playerControl = new PlayerControlConfig();
    
    public static class CommandTemplateSettings {
        public String templateFilePath = "config/ai_baritone/templates.json";
        public CommandTemplate activeTemplate = CommandTemplate.createDefault();
        public String templateVersion = "1.21.8";
    }
}
