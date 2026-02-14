#!/bin/bash
set -e

MC_VERSION=$1

declare -A VERSION_MAP=(
    ["1.15.2"]="1.15.2+build.14|0.14.22|0.44.0+1.18|6.4.90|3.2.5|3386291"
    ["1.16.5"]="1.16.5+build.9|0.14.22|0.46.6+1.19|6.4.90|3.2.5|3386295"
    ["1.17.1"]="1.17.1+build.14|0.14.22|0.55.3+1.19|6.4.90|3.2.5|3386300"
    ["1.18.2"]="1.18.2+build.4|0.14.22|0.77.0+1.18.2|6.4.90|3.2.5|3674704"
    ["1.19.2"]="1.19.2+build.28|0.14.22|0.76.0+1.19.2|8.5.103|4.1.1|4136795"
    ["1.19.4"]="1.19.4+build.5|0.14.22|0.76.0+1.19.4|11.1.118|5.2.0|4136800"
    ["1.20.1"]="1.20.1+build.10|0.14.24|0.88.1+1.20.1|11.1.118|7.0.1|4760964"
    ["1.20.4"]="1.20.4+build.3|0.15.11|0.91.1+1.20.4|11.1.118|7.2.0|5038890"
    ["1.20.6"]="1.20.6+build.3|0.15.11|0.98.0+1.20.6|14.0.127|8.0.0|5450142"
    ["1.21"]="1.21+build.1|0.15.11|0.100.0+1.21|15.0.140|11.0.0|5702821"
    ["1.21.1"]="1.21.1+build.9|0.16.0|0.105.0+1.21.1|15.0.140|11.0.1|5702821"
    ["1.21.3"]="1.21.3+build.2|0.16.5|0.108.0+1.21.3|15.0.140|13.0.0|5888771"
    ["1.21.4"]="1.21.4+build.2|0.16.7|0.110.0+1.21.4|15.0.140|13.0.1|6013712"
    ["1.21.5"]="1.21.5+build.1|0.16.9|0.112.0+1.21.5|15.0.140|13.0.1|6160010"
    ["1.21.6"]="1.21.6+build.1|0.16.9|0.113.0+1.21.6|15.0.140|13.0.1|6265642"
    ["1.21.7"]="1.21.7+build.1|0.16.9|0.114.0+1.21.7|15.0.140|13.0.1|6357793"
    ["1.21.8"]="1.21.8+build.2|0.16.9|0.115.0+1.21.8|15.0.140|13.0.1|6416791"
)

if [[ -z "${VERSION_MAP[$MC_VERSION]}" ]]; then
    echo "❌ 不支持的Minecraft版本: $MC_VERSION"
    exit 1
fi

IFS='|' read -r YARN LOADER FABRIC CLOTH MODMENU BARITONE <<< "${VERSION_MAP[$MC_VERSION]}"

sed -i "s/^minecraft_version=.*/minecraft_version=$MC_VERSION/" gradle.properties
sed -i "s/^yarn_mappings=.*/yarn_mappings=$YARN/" gradle.properties
sed -i "s/^loader_version=.*/loader_version=$LOADER/" gradle.properties
sed -i "s/^fabric_version=.*/fabric_version=$FABRIC/" gradle.properties
sed -i "s/^cloth_version=.*/cloth_version=$CLOTH/" gradle.properties
sed -i "s/^modmenu_version=.*/modmenu_version=$MODMENU/" gradle.properties

echo "✅ 已配置 Minecraft $MC_VERSION"
