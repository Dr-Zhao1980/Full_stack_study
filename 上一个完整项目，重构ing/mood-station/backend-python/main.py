from fastapi import FastAPI
from pydantic import BaseModel
import requests
import json

app = FastAPI()

# 定义请求体结构，方便解析
class MoodRequest(BaseModel):
    text: str

# 配置你的 SiliconFlow API
API_KEY = "sk-ioptkidyhppvzillnotrypqjhczvyhkigppzgcgwctwhedgs" # 替换为你提供的Key
API_URL = "https://api.siliconflow.cn/v1/chat/completions"

@app.post("/analyze")
def analyze_mood(mood: MoodRequest):
    print(f"收到心情分析请求: {mood.text}")
    
    # 构造 Prompt，强迫 AI 返回 JSON 格式
    system_prompt = """
    你是一个心情分析师。请分析用户输入的心情文本。
    必须严格以 JSON 格式返回，不要包含 markdown 标记或其他废话。
    JSON 格式要求如下：
    {
        "score": (整数, 0-100, 分数越高越开心),
        "lucky_color": (字符串, 推荐一个幸运色),
        "ai_comment": (字符串, 一句简短暖心的点评, 50字以内)
    }
    """

    payload = {
        "model": "deepseek-ai/DeepSeek-V3.2-Exp",
        "messages": [
            {"role": "system", "content": system_prompt},
            {"role": "user", "content": mood.text}
        ],
        "stream": False,
        "max_tokens": 512,
        "temperature": 0.7,
        "response_format": { "type": "json_object" } # 关键：强制 JSON 模式
    }

    headers = {
        "Authorization": f"Bearer {API_KEY}",
        "Content-Type": "application/json"
    }

    try:
        # 真正调用 AI
        response = requests.post(API_URL, json=payload, headers=headers)
        response_data = response.json()
        
        # 提取 AI 回复的内容
        ai_content = response_data['choices'][0]['message']['content']
        print("AI 返回原始内容:", ai_content)
        
        # 将字符串转为 Python 字典返回
        return json.loads(ai_content)
        
    except Exception as e:
        print(f"调用出错: {e}")
        return {
            "score": 0, 
            "lucky_color": "灰色", 
            "ai_comment": "AI 大脑暂时短路了，请检查网络或 Key。"
        }

# 启动命令: uvicorn main:app --reload --port 8000