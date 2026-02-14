package com.example.aibaritone.config.status;

import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.*;

public class PlayerControlConfig {
    public boolean enablePlayerTakeover = true;
    public double takeoverHealthThreshold = 12.0;
    public int enemyDetectionRange = 16;
    public int maxEnemiesToEngage = 5;
    
    public boolean enableCombatMode = true;
    public boolean enableSprinting = true;
    public boolean enableShieldBlocking = true;
    public boolean enableStrafe = true;
    
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int cps = 8;
    
    public boolean enableEscapeMode = true;
    public double escapeHealthThreshold = 6.0;
    public boolean enableBridgeEscape = true;
    public boolean enablePearlEscape = true;
    
    public List<String> attackBlacklist = Arrays.asList("armor_stand", "item_frame");
    public boolean autoSwitchWeapon = true;
}
