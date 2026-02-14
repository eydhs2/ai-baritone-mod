package com.example.aibaritone.takeover;

import com.example.aibaritone.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Box;
import java.util.*;

public class PlayerTakeoverManager {
    private static ModConfig config;
    private static MinecraftClient client;
    private static boolean isTakeoverActive = false;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        client = MinecraftClient.getInstance();
    }
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!config.playerControl.enablePlayerTakeover) return;
            if (client.player == null) return;
            
            checkTakeoverCondition(client.player);
        });
    }
    
    private static void checkTakeoverCondition(net.minecraft.client.network.ClientPlayerEntity player) {
        if (player.getHealth() <= config.playerControl.takeoverHealthThreshold) {
            List<LivingEntity> enemies = detectEnemies(player);
            if (!enemies.isEmpty() && !isTakeoverActive) {
                startCombatMode(player);
            }
        }
    }
    
    private static List<LivingEntity> detectEnemies(net.minecraft.client.network.ClientPlayerEntity player) {
        Box searchBox = player.getBoundingBox().expand(config.playerControl.enemyDetectionRange);
        return player.world.getEntitiesByClass(
            LivingEntity.class,
            searchBox,
            entity -> entity instanceof HostileEntity && entity.isAlive()
        );
    }
    
    private static void startCombatMode(net.minecraft.client.network.ClientPlayerEntity player) {
        isTakeoverActive = true;
        player.sendMessage(
            net.minecraft.text.Text.of("§c[AI Baritone] ⚔️ 战斗模式启动"),
            false
        );
    }
}
