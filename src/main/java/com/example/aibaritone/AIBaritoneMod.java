package com.example.aibaritone;

import com.example.aibaritone.config.ModConfig;
import com.example.aibaritone.handler.ExternalInputHandler;
import com.example.aibaritone.monitor.PlayerStatusMonitor;
import com.example.aibaritone.takeover.PlayerTakeoverManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class AIBaritoneMod implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // 注册配置
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        
        // 注册客户端生命周期事件
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            System.out.println("✅ AI Baritone Mod 已启动");
            ExternalInputHandler.start();
            PlayerStatusMonitor.init();
            PlayerTakeoverManager.init();
        });
        
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ExternalInputHandler.stop();
        });
    }
}
