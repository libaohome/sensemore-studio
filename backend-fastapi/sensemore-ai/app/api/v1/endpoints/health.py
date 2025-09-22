from datetime import datetime
from fastapi import APIRouter, Depends
from app.config import settings
from app.models.schemas import HealthResponse
from app.services.ai_service import ai_service

router = APIRouter()


@router.get("/health", response_model=HealthResponse)
async def health_check():
    """
    健康检查接口
    """
    ai_health = await ai_service.health_check()
    
    return HealthResponse(
        status="healthy",
        timestamp=datetime.now(),
        version=settings.VERSION,
        message=f"Sensemore AI Service is running. AI Service: {ai_health['status']}"
    )


@router.get("/info")
async def get_info():
    """
    获取服务信息
    """
    return {
        "name": settings.PROJECT_NAME,
        "version": settings.VERSION,
        "api_version": settings.API_V1_STR,
        "debug": settings.DEBUG,
        "available_models": ai_service.get_available_models()
    }