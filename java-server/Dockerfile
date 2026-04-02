# 1단계: 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Gradle 래퍼와 설정 파일들을 먼저 복사 (캐시 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 종속성 미리 다운로드 (소스 코드 변경 시에도 이 단계는 캐시됨)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Cloud Run은 기본적으로 8080 포트를 사용함
ENV PORT 8080
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]