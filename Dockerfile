FROM gradle:6.1-jdk8
COPY . /app
WORKDIR /app
RUN echo $selenium-url
CMD ["./gradlew", "test"]
