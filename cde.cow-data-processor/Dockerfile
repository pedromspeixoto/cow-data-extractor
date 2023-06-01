# Dockerize Spring Application
FROM anapsix/alpine-java:8_jdk
WORKDIR /app
COPY target/*.jar /app/app.jar
# Copy NewRelic Agent
#COPY newrelic/* /app/
#ENTRYPOINT ["java", "-javaagent:newrelic.jar" ,"-jar", "app.jar"]
ADD src/main/resources/deeplearning4j/motion.txt /app/motion.txt
ADD src/main/resources/deeplearning4j/models/SNNMotionModel/DL4JMotionModel.zip /app/DL4JMotionModel.zip
ENTRYPOINT ["java", "-jar", "app.jar"]