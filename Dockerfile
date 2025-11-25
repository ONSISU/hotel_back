# Use a base image with Java
FROM eclipse-temurin:17-jdk

# Directory name of Docker Container
WORKDIR /app

# Copy the built JAR file (adjust the path and name)
COPY build/libs/hotel_back.jar hotel_back.jar
# create upload directory inside container

# Create upload directory
#RUN mkdir -p /app/uploads

# Copy existing uploads (if any)
#COPY ./uploads/ /app/uploads/

# Declare as volume for persistence
VOLUME ["/app/uploads"]

# Set command to run the JAR
ENTRYPOINT ["java", "-jar", "hotel_back.jar"]