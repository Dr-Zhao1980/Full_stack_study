from pydantic import BaseModel

# 前端发来的请求格式
class MoodRequest(BaseModel):
    text: str  # 用户输入的日记/心情文本

# 返回给前端的格式
class MoodResponse(BaseModel):
    score: int          # 心情打分 (0-100)
    summary: str        # 分析摘要
    suggestion: str     # 给用户的建议
    lucky_color: str    # (可选) 幸运色