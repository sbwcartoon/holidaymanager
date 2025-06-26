# build
FROM gradle:8.14-jdk21 AS builder
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || return 0

COPY . .
RUN gradle bootJar --no-daemon

# deploy
FROM amazoncorretto:21-alpine-jdk
LABEL title="holidaymanager"
LABEL version="1.0"

### init timezone
ENV TZ Asia/Seoul
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    echo ${TZ} > /etc/timezone

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
