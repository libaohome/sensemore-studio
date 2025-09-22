from fastapi import APIRouter
from app.api.v1.endpoints import health, ai

api_router = APIRouter()

# 注册路由
api_router.include_router(health.router, tags=["健康检查"])
api_router.include_router(ai.router, prefix="/ai", tags=["AI服务"])