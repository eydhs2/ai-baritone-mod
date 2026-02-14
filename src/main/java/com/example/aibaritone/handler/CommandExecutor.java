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
            
            client.setScreen(new net.minecraft.client.gui.screen.ChatScreen(""));
            
            if (client.currentScreen instanceof net.minecraft.client.gui.screen.ChatScreen) {
                var chatScreen = (net.minecraft.client.gui.screen.ChatScreen) client.currentScreen;
                chatScreen.chatField.setText(command);
                
                client.execute(() -> {
                    chatScreen.sendMessage(command);
                });
            }
        });
    }
    
    public static void sendFeedback(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.of(message), false);
        }
    }
}
