import os
from langchain_openai import ChatOpenAI
from langchain_core.prompts import load_prompt

class GPTClient:
    def __init__(self):
        API_KEY = os.getenv("OPENAI_API_KEY")

        self.llm = ChatOpenAI(
            model="gpt-4o-mini",
            api_key=API_KEY
        )

    async def get_response(self, prompt_name: str, input_data: dict, output_parser):
        """
        src/prompts 폴더에서 특정 YAML 파일을 읽어 LLM을 실행합니다.
        """
        prompt_path = os.path.join("src", "prompt", f"{prompt_name}.yaml")
        prompt_template = load_prompt(prompt_path, encoding="utf-8")

        chain = prompt_template | self.llm | output_parser()

        return await chain.ainvoke(input_data)