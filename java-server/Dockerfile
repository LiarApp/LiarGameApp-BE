# Java 17이 설치된 리눅스 환경에서 시작
FROM eclipse-temurin:17-jdk

# 프로젝트에서 생성된 JAR 파일( ./gradlew build 시 생성되는 Java 실행 파일 )을 컨테이너 내부로 복사
COPY build/libs/*.jar app.jar

# 컨테이너가 실행될 때( e.g., docker-compose up ) 자동으로 실행될 명령
# 컨테이너: 내가 만든 프로그램만 실행되는 완전 독립된 작은 컴퓨터
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 서버 포트 명시
EXPOSE 8080
