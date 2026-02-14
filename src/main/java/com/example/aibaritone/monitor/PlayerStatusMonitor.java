package com.example.aibaritone.monitor;

import com.example.aibaritone.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PlayerStatusMonitor {
    private static ModConfig config;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 暂时禁用监控逻辑
        });
    }
}

