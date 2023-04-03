# 设置基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 拷贝 Gradle 配置文件和构建脚本到镜像中
COPY build.gradle .
COPY gradlew .
COPY gradle/ ./gradle/

# 拷贝应用程序源代码到镜像中
COPY src/ ./src/

# 运行 Gradle 构建应用程序
RUN ./gradlew build

# 拷贝构建结果到镜像中
COPY build/libs/*.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "app.jar"]