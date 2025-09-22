from typing import Generator
from fastapi import Depends, HTTPException, status
from app.config import settings


def get_settings() -> settings.__class__:
    """获取应用设置"""
    return settings


def get_current_user():
    """获取当前用户（示例依赖）"""
    # 这里可以添加用户认证逻辑
    return {"user_id": "demo_user", "username": "demo"}


def verify_api_key(api_key: str = None):
    """验证 API Key（示例）"""
    if not api_key:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="API Key is required"
        )
    # 这里可以添加 API Key 验证逻辑
    return True