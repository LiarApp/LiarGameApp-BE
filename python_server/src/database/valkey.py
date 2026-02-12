from dotenv import load_dotenv
import os
import redis
from contextlib import contextmanager

load_dotenv()
VALKEY_HOST = os.getenv("VALKEY_HOST")
VALKEY_PORT = int(os.getenv("VALKEY_PORT"))
VALKEY_PASSWORD = os.getenv("VALKEY_PASSWORD")

@contextmanager
def get_valkey_db():
    db = redis.Redis(
        host=VALKEY_HOST,
        port=VALKEY_PORT,
        password=VALKEY_PASSWORD,
        decode_responses=True,
        ssl=True,
        ssl_cert_reqs=None
    )

    try:
        yield db
    finally:
        db.close()