import pytest
from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)


def test_root():
    """测试根路径"""
    response = client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert "message" in data
    assert "version" in data


def test_ping():
    """测试 ping 接口"""
    response = client.get("/ping")
    assert response.status_code == 200
    data = response.json()
    assert data["message"] == "pong"


def test_health_check():
    """测试健康检查接口"""
    response = client.get("/api/v1/health")
    assert response.status_code == 200
    data = response.json()
    assert data["status"] == "healthy"
    assert "timestamp" in data
    assert "version" in data


def test_get_info():
    """测试获取服务信息接口"""
    response = client.get("/api/v1/info")
    assert response.status_code == 200
    data = response.json()
    assert "name" in data
    assert "version" in data
    assert "available_models" in data


def test_get_available_models():
    """测试获取可用模型接口"""
    response = client.get("/api/v1/ai/models")
    assert response.status_code == 200
    data = response.json()
    assert data["success"] is True
    assert "models" in data["data"]


def test_chat_with_ai():
    """测试 AI 对话接口"""
    test_request = {
        "prompt": "你好，这是一个测试",
        "model": "sensemore-ai-model",
        "temperature": 0.7,
        "max_tokens": 100
    }
    
    response = client.post("/api/v1/ai/chat", json=test_request)
    assert response.status_code == 200
    data = response.json()
    assert "result" in data
    assert "model" in data
    assert "usage" in data
    assert "processing_time" in data


def test_analyze_text():
    """测试文本分析接口"""
    test_request = {
        "prompt": "这是一个很好的产品，我非常喜欢！",
        "temperature": 0.5
    }
    
    response = client.post("/api/v1/ai/analyze", json=test_request)
    assert response.status_code == 200
    data = response.json()
    assert data["success"] is True
    assert "analysis" in data["data"]
    analysis = data["data"]["analysis"]
    assert "sentiment" in analysis
    assert "keywords" in analysis
    assert "language" in analysis


def test_get_ai_status():
    """测试 AI 服务状态接口"""
    response = client.get("/api/v1/ai/status")
    assert response.status_code == 200
    data = response.json()
    assert data["success"] is True
    assert "service" in data["data"]


def test_invalid_ai_request():
    """测试无效的 AI 请求"""
    # 测试空提示
    test_request = {
        "prompt": "",
        "temperature": 0.7
    }
    
    response = client.post("/api/v1/ai/chat", json=test_request)
    assert response.status_code == 422  # 验证错误
    
    # 测试温度参数超出范围
    test_request = {
        "prompt": "测试",
        "temperature": 3.0  # 超出 2.0 的限制
    }
    
    response = client.post("/api/v1/ai/chat", json=test_request)
    assert response.status_code == 422  # 验证错误


@pytest.mark.asyncio
async def test_ai_service():
    """测试 AI 服务类"""
    from app.services.ai_service import ai_service
    from app.models.schemas import AIRequest
    
    request = AIRequest(
        prompt="测试 AI 服务",
        temperature=0.5
    )
    
    response = await ai_service.process_request(request)
    assert response.result is not None
    assert response.model is not None
    assert response.usage is not None
    assert response.processing_time is not None
    
    # 测试健康检查
    health = await ai_service.health_check()
    assert health["status"] == "healthy"
    assert health["service"] == "ai_service"