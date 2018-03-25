FROM openjdk:jdk-alpine

COPY MTServer.class /deployments/
COPY ClientHandler.class /deployments/

CMD cd /deployments; java MTServer

