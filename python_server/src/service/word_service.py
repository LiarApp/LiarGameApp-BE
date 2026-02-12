from src.client.gpt_client import GPTClient
from langchain_core.output_parsers import JsonOutputParser

class WordService:
    def __init__(self):
        self.gpt_client = GPTClient()

    async def generate_and_save_word(self, category: str):
        words_data = await self.gpt_client.run_prompt(
            task_name="generate_word",
            input_data=category,
            parser=JsonOutputParser
        )

    def get_word(self):
        return "word"