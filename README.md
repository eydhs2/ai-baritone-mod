# 🤖 AI Baritone 模组 · 智能 Minecraft 助手

[![构建状态](https://github.com/Eydhs2/ai-baritone-mod/actions/workflows/build.yml/badge.svg)](https://github.com/Eydhs2/ai-baritone-mod/actions/workflows/build.yml)
[![发布状态](https://github.com/Eydhs2/ai-baritone-mod/actions/workflows/release.yml/badge.svg)](https://github.com/Eydhs2/ai-baritone-mod/actions/workflows/release.yml)
[![支持版本](https://img.shields.io/badge/Minecraft-1.15.2--1.21.8-blue)](https://github.com/Eydhs2/ai-baritone-mod)
[![许可证](https://img.shields.io/badge/License-LGPL--3.0-green)](LICENSE)

---

## 🌟 项目简介

**AI Baritone 模组**是一个基于大模型技术的智能 Minecraft 助手，将自然语言指令转换为 Baritone 自动寻路命令，并实时监控玩家状态，在危险时自动战斗/逃生。

### ✨ 核心特性

| 特性 | 描述 |
|------|------|
| 🧠 **多模型支持** | 通义千问、豆包、GLM、DeepSeek、OpenAI、Kimi、文心一言、Ollama |
| 🗣️ **自然语言控制** | ""去100 64 200"" → #goto 100 64 200 |
| ⚔️ **自动战斗** | 血量过低时自动攻击敌人、举盾格挡、走位躲避 |
| 🏃 **智能逃生** | 濒死时自动搭路、投掷末影珍珠、寻找安全位置 |
| ❤️ **状态监控** | 实时检测血量/饥饿/氧气，低于阈值自动停船补血 |
| 🔄 **任务恢复** | 安全后自动继续之前的挖矿/寻路任务 |
| 📦 **全版本支持** | 兼容 Minecraft 1.15.2 至 1.21.8 共17个版本 |
| ⚙️ **高度可配置** | ModMenu 图形化界面，所有参数实时调整 |

---

## 🚀 快速开始

### 📥 安装步骤

1. **安装依赖模组**（全部放入 mods 文件夹）
   - [Fabric API](https://modrinth.com/mod/fabric-api)
   - [Baritone API](https://www.curseforge.com/minecraft/mc-mods/baritone)
   - [ModMenu](https://modrinth.com/mod/modmenu)
   - [Cloth Config API](https://modrinth.com/mod/cloth-config)

2. **下载本模组**
   - 从 [Releases](https://github.com/Eydhs2/ai-baritone-mod/releases) 下载对应你MC版本的 JAR
   - 或从 [Actions](https://github.com/Eydhs2/ai-baritone-mod/actions) 下载最新构建

3. **配置大模型**（可选）
   - 启动游戏 → ModMenu → AI Baritone 配置
   - 选择你的AI服务商，填入API密钥

4. **开始使用**
   - 按 T 打开聊天框
   - 输入自然语言指令

---

## 🎮 使用示例

| 你说 | 模组自动补全 |
|------|-------------|
| 去100 64 200 | #goto 100 64 200 |
| 挖钻石 | #mine diamond_ore |
| 跟随Steve | #follow Steve |
| 停止 | #cancel |

---

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (git checkout -b feature/amazing-feature)
3. 提交修改 (git commit -m 'feat: 添加某个功能')
4. 推送分支 (git push origin feature/amazing-feature)
5. 提交 Pull Request

---

## 📄 许可证

[LGPL-3.0 License](LICENSE)

---

## ⭐ 支持项目

如果这个模组对你有帮助，欢迎给仓库点个 Star ⭐

**Happy Mining! 🎮**
