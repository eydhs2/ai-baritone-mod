package com.example.aibaritone.handler;

import com.example.aibaritone.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ExternalInputHandler {
    private static ModConfig config;
    
    static {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static void start() {
        System.out.println("✅ 外部输入处理器已启动，端口: " + config.serverPort);
    }
    
    public static void stop() {
        System.out.println("外部输入处理器已停止");
    }
}
