package com.example.aibaritone.handler;

import com.example.aibaritone.config.ModConfig;
import com.example.aibaritone.config.ai.AIService;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import java.net.*;
import java.io.*;

public class ExternalInputHandler {
    private static ServerSocket serverSocket;
    private static Thread listenerThread;
    private static ModConfig config;
    
    public static void start() {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (!config.enableAICompletion) return;
        
        listenerThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(config.serverPort);
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                    );
                    String command = in.readLine();
                    
                    MinecraftClient.getInstance().execute(() -> {
                        AIService.convertToCommand(command).thenAccept(cmd -> {
                            MinecraftClient.getInstance().execute(() -> {
                                CommandExecutor.executeImmediate(cmd, "外部指令");
                            });
                        });
                    });
                    
                    socket.close();
                }
            } catch (Exception e) {
                if (config.debugMode) e.printStackTrace();
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
    
    public static void stop() {
        try {
            if (serverSocket != null) serverSocket.close();
            if (listenerThread != null) listenerThread.interrupt();
        } catch (Exception e) {}
    }
}
