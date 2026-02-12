from dotenv import load_dotenv
import os
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import load_prompt

load_dotenv()
OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")

class GPTClient:
    def __init__(self):
        self.llm = ChatOpenAI(
            model="gpt-4o-mini",
            temperature=0.7,
            openai_api_key=OPENAI_API_KEY
        )

    async def run_prompt(self, task_name: str, input_data: str, parser_class: StrOutputParser):
        prompt_path = os.path.join("src", "prompt", f"{task_name}.yaml")
        prompt_template = load_prompt(prompt_path, encoding="utf-8")

        parser = parser_class()
        
        chain = prompt_template | self.llm | parser

        return await chain.ainvoke(input_data)
