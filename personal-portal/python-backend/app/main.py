from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.routers import mood
# 未来引入其他项目: from app.routers import tetris, crawler ...

app = FastAPI(title="Dr.Zhao's AI Services")

# 1. 配置 CORS (允许前端和 Java 后端访问)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 生产环境建议改为 ["http://localhost:5173", "http://localhost:8080"]
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# 2. 注册路由 (插上插头)
app.include_router(mood.router) 
# app.include_router(tetris.router, prefix="/api")

@app.get("/")
def root():
    return {"message": "AI Services Online 🟢"}
