package com.example.aibaritone.monitor;

import com.example.aibaritone.config.ModConfig;
import com.example.aibaritone.handler.CommandExecutor;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerStatusMonitor {
    private static ModConfig config;
    private static boolean isEmergency = false;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!config.playerStatus.enableHealthMonitor) return;
            if (client.player == null) return;
            
            long currentTick = client.world.getTime();
            if (currentTick % (config.playerStatus.checkInterval * 20) != 0) return;
            
            checkPlayerStatus(client.player);
        });
    }
    
    private static void checkPlayerStatus(PlayerEntity player) {
        float health = player.getHealth();
        if (health <= config.playerStatus.healthThreshold) {
            if (!isEmergency) {
                isEmergency = true;
                CommandExecutor.executeImmediate("#cancel", "紧急停止 - 血量过低");
            }
            return;
        }
        
        if (health > config.playerStatus.healthThreshold + 4) {
            isEmergency = false;
        }
    }
    
    public static void resumeLastTask() {
        // 恢复任务逻辑
    }
}
