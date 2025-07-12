# Use OpenJDK image
FROM openjdk:21-jdk

# Set working directory
WORKDIR /app

# Copy source code
COPY chatapp/src/chatapp ./chatapp


# Compile Java files
RUN javac chatapp/server.java

# Run the server
CMD ["java", "chatapp.server"]