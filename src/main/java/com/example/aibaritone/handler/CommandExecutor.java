package com.example.aibaritone.handler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class CommandExecutor {
    
    public static void executeImmediate(String command, String reason) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        client.execute(() -> {
            client.player.sendMessage(
                Text.of("§7[AI Baritone] ⚡ " + reason + ": §f" + command),
                false
            );
            
            // 直接发送指令到聊天框
            client.player.networkHandler.sendChatCommand(command.replace("#", ""));
        });
    }
}

