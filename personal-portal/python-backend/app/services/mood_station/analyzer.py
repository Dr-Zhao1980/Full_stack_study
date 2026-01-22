import httpx
from app.core.config import settings
from app.schemas.mood import MoodResponse

class MoodAnalyzer:
    def __init__(self):
        self.api_key = settings.AI_API_KEY
        self.base_url = settings.AI_BASE_URL
        self.model = settings.AI_MODEL_NAME

    async def analyze(self, text: str) -> MoodResponse:
        """
        调用 LLM 进行情绪分析
        """
        # 构造 Prompt，让 AI 返回特定格式 (最好是 JSON)
        system_prompt = """
        你是一个专业的情绪分析师。请分析用户的输入，并以 JSON 格式返回结果。
        必须包含以下字段：
        - score: 0到100的整数，表示积极程度。
        - summary: 一句简短的情绪总结。
        - suggestion: 一句温暖的建议。
        - lucky_color: 一个推荐的颜色（中文）。
        不要返回 Markdown 格式，只返回纯 JSON。
        """

        try:
            async with httpx.AsyncClient() as client:
                response = await client.post(
                    f"{self.base_url}/chat/completions",
                    headers={"Authorization": f"Bearer {self.api_key}"},
                    json={
                        "model": self.model,
                        "messages": [
                            {"role": "system", "content": system_prompt},
                            {"role": "user", "content": text}
                        ],
                        "temperature": 0.7
                    },
                    timeout=30.0
                )
                
                if response.status_code != 200:
                    # 如果 API 报错，返回兜底数据
                    print(f"API Error: {response.text}")
                    return self._fallback_response()

                data = response.json()
                content = data["choices"][0]["message"]["content"]
                
                # 解析 AI 返回的 JSON 字符串 (这里做个简单的演示，实际可能需要更严谨的 JSON 解析)
                import json
                try:
                    # 清理可能存在的 markdown 符号
                    clean_content = content.replace("```json", "").replace("```", "").strip()
                    result_dict = json.loads(clean_content)
                    return MoodResponse(**result_dict)
                except Exception as e:
                    print(f"JSON Parse Error: {e}")
                    return self._fallback_response()

        except Exception as e:
            print(f"Network Error: {e}")
            return self._fallback_response()

    def _fallback_response(self):
        """当 AI 服务挂了时的备用方案"""
        return MoodResponse(
            score=50,
            summary="AI 正在打盹...",
            suggestion="深呼吸，休息一下再试吧。",
            lucky_color="灰色"
        )

# 实例化一个全局对象供调用
analyzer = MoodAnalyzer()