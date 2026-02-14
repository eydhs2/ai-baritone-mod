#!/bin/bash
set -e

VERSIONS=(
    "1.15.2" "1.16.5" "1.17.1" "1.18.2" "1.19.2" "1.19.4"
    "1.20.1" "1.20.4" "1.20.6" "1.21" "1.21.1" "1.21.3"
    "1.21.4" "1.21.5" "1.21.6" "1.21.7" "1.21.8"
)

mkdir -p builds

for VERSION in "${VERSIONS[@]}"; do
    echo "========================================="
    echo "构建 Minecraft $VERSION"
    echo "========================================="
    
    ./scripts/configure-version.sh "$VERSION"
    ./gradlew clean build --no-daemon
    
    cp build/libs/ai-baritone-helper-*.jar "builds/ai-baritone-helper-$VERSION.jar"
    echo "✅ 完成: $VERSION"
done

echo "========================================="
echo "🎉 全版本构建完成！"
ls -lh builds/
