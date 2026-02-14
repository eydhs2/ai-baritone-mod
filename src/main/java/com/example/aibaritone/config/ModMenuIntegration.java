package com.example.aibaritone.config;

import com.example.aibaritone.config.ai.AIConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
            
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("AI Baritone 配置"));
            
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            
            // 基本设置
            ConfigCategory general = builder.getOrCreateCategory(Text.of("⚙️ 基本设置"));
            general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("启用AI补全"), config.enableAICompletion)
                .setDefaultValue(true)
                .setSaveConsumer(v -> config.enableAICompletion = v)
                .build()
            );
            
            // AI 配置
            ConfigCategory aiCategory = builder.getOrCreateCategory(Text.of("🤖 大模型配置"));
            
            aiCategory.addEntry(entryBuilder
                .startEnumSelector(
                    Text.of("AI服务商"),
                    AIConfig.AIProvider.class,
                    config.ai.provider
                )
                .setDefaultValue(AIConfig.AIProvider.OLLAMA)
                .setTooltip(Text.of(
                    "选择AI服务提供商\n" +
                    "• Ollama - 本地运行，免费\n" +
                    "• DeepSeek - 性价比高\n" +
                    "• OpenAI - GPT系列\n" +
                    "• 通义千问 - 阿里云\n" +
                    "• 豆包 - 字节跳动\n" +
                    "• 智谱清言 - GLM系列\n" +
                    "• Kimi - 月之暗面\n" +
                    "• 文心一言 - 百度"
                ))
                .setSaveConsumer(newValue -> {
                    config.ai.provider = newValue;
                    config.ai.applyDefaults();
                })
                .build()
            );
            
            aiCategory.addEntry(entryBuilder
                .startStrField(Text.of("API密钥"), config.ai.apiKey)
                .setDefaultValue("")
                .setTooltip(Text.of("从各平台获取的API密钥"))
                .setSaveConsumer(v -> config.ai.apiKey = v)
                .build()
            );
            
            return builder.build();
        };
    }
}
