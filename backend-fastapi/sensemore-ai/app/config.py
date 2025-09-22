from typing import Any, Dict, List, Optional, Union
from pydantic import AnyHttpUrl, field_validator
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """应用配置"""
    
    # 基本配置
    PROJECT_NAME: str = "Sensemore AI"
    VERSION: str = "0.1.0"
    API_V1_STR: str = "/api/v1"
    
    # 服务器配置
    HOST: str = "0.0.0.0"
    PORT: int = 8000
    DEBUG: bool = True
    
    # CORS 配置 - 简化版本
    BACKEND_CORS_ORIGINS: List[str] = []
    
    @field_validator("BACKEND_CORS_ORIGINS", mode="before")
    @classmethod
    def assemble_cors_origins(cls, v: Union[str, List[str]]) -> List[str]:
        if isinstance(v, str):
            return [origin.strip() for origin in v.split(",") if origin.strip()]
        elif isinstance(v, list):
            return v
        return []
    
    # 安全配置
    SECRET_KEY: str = "your-secret-key-change-this-in-production"
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 30
    
    # 数据库配置 (如果需要)
    DATABASE_URL: Optional[str] = None
    
    # AI 服务配置
    AI_MODEL_PATH: Optional[str] = None
    AI_API_KEY: Optional[str] = None
    
    class Config:
        # env_file = ".env"  # 暂时禁用读取 .env 文件
        case_sensitive = True
        extra = "ignore"  # 忽略额外的环境变量


settings = Settings()