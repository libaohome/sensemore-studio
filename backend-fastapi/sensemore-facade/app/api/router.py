from fastapi import APIRouter

from app.api.v1.endpoints import auth, users, health, fastmcp

# 创建API路由器
api_router = APIRouter()

# 注册各个端点路由
api_router.include_router(auth.router, prefix="/auth", tags=["认证"])
api_router.include_router(users.router, prefix="/users", tags=["用户"])
api_router.include_router(health.router, prefix="/health", tags=["健康检查"])
api_router.include_router(fastmcp.router, prefix="/fastmcp", tags=["FastMCP"])
