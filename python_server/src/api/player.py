from fastapi import APIRouter

router = APIRouter(
    prefix="/api/player",
    tags=["AI Player"]
)

@router.get("")
def read_player():
    return "hello"