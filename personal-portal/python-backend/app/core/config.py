import os
from dotenv import load_dotenv

load_dotenv()


class Settings:
    AI_API_KEY: str | None = os.getenv("AI_API_KEY")
    AI_BASE_URL: str | None = os.getenv("AI_BASE_URL")
    AI_MODEL_NAME: str = os.getenv("AI_MODEL_NAME", "gpt-3.5-turbo")


settings = Settings()
