#!/bin/bash
# 启动 Sensemore AI 服务

echo "🚀 启动 Sensemore AI Backend Service..."

# 检查 Python 版本
python_version=$(python3 --version 2>&1)
echo "Python 版本: $python_version"

# 安装依赖
echo "📦 安装依赖..."
pip install -e .

# 启动服务
echo "🌟 启动服务..."
echo "API 文档将在 http://localhost:8000/docs 提供"
echo "按 Ctrl+C 停止服务"

python -m app.main