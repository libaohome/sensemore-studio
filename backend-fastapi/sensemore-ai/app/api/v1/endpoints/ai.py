from fastapi import APIRouter, HTTPException, status, Depends
from app.models.schemas import AIRequest, AIResponse, BaseResponse, ErrorResponse
from app.services.ai_service import ai_service
from app.api.deps import get_current_user

router = APIRouter()


@router.post("/chat", response_model=AIResponse)
async def chat_with_ai(
    request: AIRequest,
    current_user: dict = Depends(get_current_user)
):
    """
    与 AI 进行对话
    
    - **prompt**: 发送给 AI 的提示文本
    - **model**: 使用的 AI 模型（可选）
    - **temperature**: 生成温度参数，控制输出的随机性（0.0-2.0）
    - **max_tokens**: 最大生成 token 数量
    """
    try:
        response = await ai_service.process_request(request)
        return response
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"AI processing failed: {str(e)}"
        )


@router.get("/models")
async def get_available_models():
    """
    获取可用的 AI 模型列表
    """
    try:
        models = ai_service.get_available_models()
        return BaseResponse(
            success=True,
            message="Successfully retrieved available models",
            data={"models": models}
        )
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to get models: {str(e)}"
        )


@router.post("/analyze", response_model=BaseResponse)
async def analyze_text(
    request: AIRequest,
    current_user: dict = Depends(get_current_user)
):
    """
    文本分析接口
    
    对输入的文本进行深度分析，包括情感分析、关键词提取等
    """
    try:
        # 模拟文本分析逻辑
        analysis_result = {
            "sentiment": "positive" if "好" in request.prompt or "great" in request.prompt.lower() else "neutral",
            "keywords": request.prompt.split()[:5],  # 简单的关键词提取
            "length": len(request.prompt),
            "language": "chinese" if any('\u4e00' <= char <= '\u9fff' for char in request.prompt) else "english",
            "complexity": "high" if len(request.prompt) > 100 else "medium" if len(request.prompt) > 50 else "low"
        }
        
        return BaseResponse(
            success=True,
            message="Text analysis completed successfully",
            data={"analysis": analysis_result}
        )
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Text analysis failed: {str(e)}"
        )


@router.get("/status")
async def get_ai_status():
    """
    获取 AI 服务状态
    """
    try:
        health_status = await ai_service.health_check()
        return BaseResponse(
            success=True,
            message="AI service status retrieved successfully",
            data=health_status
        )
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to get AI status: {str(e)}"
        )