# settings.py
import os
from typing import List, Union
from dotenv import load_dotenv

# 加载 .env 文件
load_dotenv()

class Settings:
    # Postgres 数据库配置
    POSTGRES_SERVER = os.getenv("POSTGRES_SERVER", "localhost")
    POSTGRES_PORT = int(os.getenv("POSTGRES_PORT", 5432))
    POSTGRES_DB = os.getenv("POSTGRES_DB", "app")
    POSTGRES_USER = os.getenv("POSTGRES_USER", "postgres")
    POSTGRES_PASSWORD = os.getenv("POSTGRES_PASSWORD", "changethis")
    
    # Redis 配置
    REDIS_SERVER = os.getenv("REDIS_SERVER", "localhost")
    REDIS_PORT = int(os.getenv("REDIS_PORT", 6379))
    
    # MySQL 数据库配置
    MYSQL_SERVER = os.getenv("MYSQL_SERVER", "localhost")
    MYSQL_PORT = int(os.getenv("MYSQL_PORT", 3306))
    MYSQL_DB = os.getenv("MYSQL_DB", "app")
    MYSQL_USER = os.getenv("MYSQL_USER", "root")
    MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD", "changethis")
    
    # 应用配置
    PROJECT_NAME = os.getenv("PROJECT_NAME", "FastAPI-FastMCP项目")
    PROJECT_DESCRIPTION = os.getenv("PROJECT_DESCRIPTION", "使用FastAPI和FastMCP构建的高性能API服务")
    VERSION = os.getenv("VERSION", "0.1.0")
    API_PREFIX = os.getenv("API_PREFIX", "/api")
    DEBUG_MODE = os.getenv("DEBUG_MODE", "True").lower() == "true"
    
    # 服务器配置
    SERVER_HOST = os.getenv("SERVER_HOST", "0.0.0.0")
    SERVER_PORT = int(os.getenv("SERVER_PORT", 8000))
    
    # CORS 配置
    CORS_ORIGINS: List[str] = ["http://localhost:3000", "http://localhost:8080"]
    @field_validator("CORS_ORIGINS")
    def assemble_cors_origins(cls, v: Union[str, List[str]]) -> List[str]:
        if isinstance(v, str) and not v.startswith("["):
            return [i.strip() for i in v.split(",")]
        elif isinstance(v, (list, str)):
            return v
        raise ValueError(v)
# 创建全局配置实例
settings = Settings()