package com.example.aibaritone.config.ai;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class AIConfig {
    
    public enum AIProvider {
        OLLAMA("Ollama (本地)"),
        DEEPSEEK("DeepSeek"),
        OPENAI("OpenAI"),
        QWEN("通义千问 (Qwen)"),
        DOUBAO("豆包 (Doubao)"),
        GLM("智谱清言 (GLM)"),
        KIMI("Kimi"),
        WENXIN("文心一言");
        
        public final String displayName;
        AIProvider(String displayName) { this.displayName = displayName; }
    }
    
    @ConfigEntry.Gui.Tooltip
    public AIProvider provider = AIProvider.OLLAMA;
    
    @ConfigEntry.Gui.PasswordField
    public String apiKey = "";
    
    public String apiEndpoint = "";
    public String modelName = "";
    
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 5, max = 120)
    public int timeoutSeconds = 30;
    
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
    public int maxRetries = 3;
    
    public boolean enableStreaming = true;
    public boolean fallbackToOllama = true;
    public boolean showThinking = true;
    
    @ConfigEntry.Gui.Tooltip(count = 3)
    public String systemPrompt = "你是一个Minecraft Baritone助手。" +
        "将自然语言转成Baritone指令。只输出指令本身，不要解释。\n" +
        "可用指令：\n" +
        "- #goto x y z : 前往坐标\n" +
        "- #mine 矿物 : 自动挖矿\n" +
        "- #follow 玩家 : 跟随玩家\n" +
        "- #cancel : 停止";
    
    public void applyDefaults() {
        switch (provider) {
            case OLLAMA:
                apiEndpoint = "http://localhost:11434/api/chat";
                modelName = "qwen2.5:7b";
                apiKey = "";
                break;
                
            case DEEPSEEK:
                apiEndpoint = "https://api.deepseek.com/v1/chat/completions";
                modelName = "deepseek-chat";
                break;
                
            case OPENAI:
                apiEndpoint = "https://api.openai.com/v1/chat/completions";
                modelName = "gpt-3.5-turbo";
                break;
                
            case QWEN:
                apiEndpoint = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
                modelName = "qwen-plus";
                break;
                
            case DOUBAO:
                apiEndpoint = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";
                modelName = "doubao-lite-32k";
                break;
                
            case GLM:
                apiEndpoint = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
                modelName = "glm-4";
                break;
                
            case KIMI:
                apiEndpoint = "https://api.moonshot.cn/v1/chat/completions";
                modelName = "moonshot-v1-8k";
                break;
                
            case WENXIN:
                apiEndpoint = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions";
                modelName = "ernie-3.5-8k";
                break;
        }
    }
}
