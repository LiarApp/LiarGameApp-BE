from sqlalchemy.orm import declarative_base
from src.database.mysql import engine

Base = declarative_base()

def init_db():
    Base.metadata.create_all(bind=engine)