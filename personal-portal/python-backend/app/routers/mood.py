from fastapi import APIRouter, HTTPException
from app.schemas.mood import MoodRequest, MoodResponse
from app.services.mood_station.analyzer import analyzer

router = APIRouter(
    prefix="/mood",    # 接口前缀，访问时是 /api/mood
    tags=["Mood Station"]
)

@router.post("/analyze", response_model=MoodResponse)
async def analyze_mood(request: MoodRequest):
    """
    接收文本，返回心情分析结果
    """
    if not request.text.strip():
        raise HTTPException(status_code=400, detail="Text cannot be empty")
    
    result = await analyzer.analyze(request.text)
    return result