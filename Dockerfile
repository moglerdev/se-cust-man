FROM alpine:3.18.0 as base

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . /app

# Install SBT
RUN curl -sL "https://github.com/sbt/sbt/releases/download/v1.5.5/sbt-1.5.5.tgz" | tar -xz -C /usr/local

# Set the PATH to include SBT
ENV PATH="/usr/local/sbt/bin:${PATH}"

# Build the application using SBT
RUN sbt compile

# Specify the default command to run the application
CMD sbt run
