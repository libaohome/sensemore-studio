from contextlib import asynccontextmanager
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse

from app.config import settings
from app.api.v1.api import api_router

@asynccontextmanager
async def lifespan(app: FastAPI):
    """应用生命周期管理"""
    # 启动时执行
    print(f"🚀 {settings.PROJECT_NAME} v{settings.VERSION} 正在启动...")
    print(f"📚 API 文档: http://{settings.HOST}:{settings.PORT}/docs")
    print(f"🔗 API 地址: http://{settings.HOST}:{settings.PORT}{settings.API_V1_STR}")
    
    yield
    
    # 关闭时执行
    print(f"👋 {settings.PROJECT_NAME} 正在关闭...")


# 创建 FastAPI 应用实例
app = FastAPI(
    title=settings.PROJECT_NAME,
    version=settings.VERSION,
    description="Sensemore AI Backend Service - 基于 FastAPI 的智能化后端服务",
    openapi_url=f"{settings.API_V1_STR}/openapi.json",
    docs_url="/docs",
    redoc_url="/redoc",
    lifespan=lifespan
)

# 设置 CORS
if settings.BACKEND_CORS_ORIGINS:
    app.add_middleware(
        CORSMiddleware,
        allow_origins=[str(origin) for origin in settings.BACKEND_CORS_ORIGINS],
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )


# 注册 API 路由
app.include_router(api_router, prefix=settings.API_V1_STR)


@app.get("/")
async def root():
    """根路径"""
    return {
        "message": "欢迎使用 Sensemore AI Backend Service",
        "version": settings.VERSION,
        "docs": "/docs",
        "redoc": "/redoc",
        "api": settings.API_V1_STR
    }


@app.get("/ping")
async def ping():
    """简单的ping接口"""
    return {"message": "pong"}


# 全局异常处理
@app.exception_handler(Exception)
async def global_exception_handler(request, exc):
    """全局异常处理器"""
    return JSONResponse(
        status_code=500,
        content={
            "error": "Internal Server Error",
            "message": "服务器内部错误，请稍后重试",
            "detail": str(exc) if settings.DEBUG else None
        }
    )

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app.main:app",
        host=settings.HOST,
        port=settings.PORT,
        reload=settings.DEBUG
    )
