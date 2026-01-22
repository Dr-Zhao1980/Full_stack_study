# test.py 完整代码
# 从 app/core 文件夹下导入 config 模块，再取里面的 settings 实例
from app.core.config import settings

# 打印配置验证
print("AI密钥：", settings.AI_API_KEY)
print("AI地址：", settings.AI_BASE_URL)
print("AI模型：", settings.AI_MODEL_NAME)

