from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.api import deps
# from app.db.session import get_db

router = APIRouter()


@router.get("/")
def health_check():
    """
    健康检查端点
    """
    # 使用字典直接返回，让FastAPI处理序列化
    return {"status": "ok", "message": "服务运行正常"}


# @router.get("/db")
# def db_health_check(db: Session = Depends(get_db)):
#     """
#     数据库健康检查端点
#     """
#     try:
#         # 尝试执行一个简单的查询
#         db.execute("SELECT 1")
#         return {"status": "ok", "message": "数据库连接正常"}
#     except Exception as e:
#         return {"status": "error", "message": f"数据库连接异常: {str(e)}"}