from src.database.valkey import get_valkey_db

class WordRepository:
    @staticmethod
    async def save():
        with get_valkey_db() as db:
            