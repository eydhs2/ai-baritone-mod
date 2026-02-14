package com.example.aibaritone.config.status;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class PlayerStatusConfig {
    public boolean enableHealthMonitor = true;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int healthThreshold = 8;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int foodThreshold = 10;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
    public int airThreshold = 60;
    
    public boolean enableAutoHeal = true;
    public boolean enableAutoFeed = true;
    public boolean enableAutoEscape = true;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 60)
    public int checkInterval = 2;
    
    public boolean autoResumeTask = true;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 30)
    public int resumeDelay = 5;
}
