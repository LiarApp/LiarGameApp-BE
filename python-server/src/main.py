from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from dotenv import load_dotenv
from src.entity.base import init_db
import uvicorn

load_dotenv()

def lifespan(app: FastAPI):
    init_db()

    yield

app = FastAPI(
    description="라이어 게임 파이썬 서버입니다.",
    lifespan=lifespan
)

app.add_middleward(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

@app.get("/")
def read_root():
    return {"message": "라이어 게임 파이썬 서버에 오신 것을 환영합니다."}

if __name__ == "__main__":
    uvicorn.run("src.main:app", host="0.0.0.0", port=8000, reload=True)