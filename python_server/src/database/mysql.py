from dotenv import load_dotenv
import os
import pymysql
from contextlib import contextmanager

load_dotenv()
os.getenv("")
os.getenv("")
os.getenv("")
os.getenv("")

@contextmanager
def get_mysql_db():
    db = pymysql.connect(

    )

    try:
        yield db
    finally:
        db.close()