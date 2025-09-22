from typing import Optional
from datetime import datetime
from pydantic import BaseModel, Field


class HealthResponse(BaseModel):
    """健康检查响应模型"""
    status: str = "healthy"
    timestamp: datetime = Field(default_factory=datetime.now)
    version: str
    message: Optional[str] = None


class AIRequest(BaseModel):
    """AI 请求模型"""
    prompt: str = Field(..., description="AI 处理的提示文本", min_length=1, max_length=1000)
    model: Optional[str] = Field(default="default", description="使用的AI模型")
    temperature: Optional[float] = Field(default=0.7, ge=0.0, le=2.0, description="生成温度参数")
    max_tokens: Optional[int] = Field(default=100, ge=1, le=2000, description="最大token数量")


class AIResponse(BaseModel):
    """AI 响应模型"""
    result: str = Field(..., description="AI 生成的结果")
    model: str = Field(..., description="使用的模型")
    usage: Optional[dict] = Field(default=None, description="使用统计")
    processing_time: Optional[float] = Field(default=None, description="处理时间(秒)")


class ErrorResponse(BaseModel):
    """错误响应模型"""
    error: str = Field(..., description="错误类型")
    message: str = Field(..., description="错误信息")
    detail: Optional[str] = Field(default=None, description="详细错误信息")


class BaseResponse(BaseModel):
    """基础响应模型"""
    success: bool = Field(..., description="请求是否成功")
    message: Optional[str] = Field(default=None, description="响应信息")
    data: Optional[dict] = Field(default=None, description="响应数据")