# Sensemore AI Backend Service

这是 Sensemore AI 的后端服务，基于 FastAPI 框架构建。

## 快速开始

### 安装依赖

```bash
pip install -e .
```

### 开发模式安装

```bash
pip install -e ".[dev]"
```

### 启动服务

```bash
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

或者使用环境变量：

```bash
python -m app.main
```

### API 文档

启动服务后，访问以下地址查看 API 文档：

- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

## 项目结构

```
sensemore-ai/
├── app/
│   ├── __init__.py
│   ├── main.py              # FastAPI 应用入口
│   ├── config.py            # 配置管理
│   ├── api/
│   │   ├── __init__.py
│   │   ├── deps.py          # 依赖注入
│   │   └── v1/
│   │       ├── __init__.py
│   │       ├── api.py       # API 路由汇总
│   │       └── endpoints/
│   │           ├── __init__.py
│   │           ├── health.py
│   │           └── ai.py
│   ├── core/
│   │   ├── __init__.py
│   │   ├── config.py        # 核心配置
│   │   └── security.py      # 安全相关
│   ├── models/
│   │   ├── __init__.py
│   │   └── schemas.py       # Pydantic 模型
│   └── services/
│       ├── __init__.py
│       └── ai_service.py    # AI 服务逻辑
├── tests/
│   ├── __init__.py
│   └── test_main.py
├── .env
├── pyproject.toml
└── README.md
```

## 环境配置

复制 `.env` 文件并根据需要修改配置：

```bash
cp .env.example .env
```

## 测试

```bash
pytest
```

## 开发

### 代码格式化

```bash
black app tests
isort app tests
```

### 类型检查

```bash
mypy app
```

### 代码检查

```bash
flake8 app
```