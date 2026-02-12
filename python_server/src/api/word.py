from fastapi import APIRouter
from src.service.word_service import WordService

router = APIRouter(
    prefix="/api/word",
    tags=["Word"]
)

word_service = WordService()

@router.get("")
def read_word(category: str):
    return word_service.get_word(category)