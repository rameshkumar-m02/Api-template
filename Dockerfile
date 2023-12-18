FROM openjdk:17
ADD target/graphql-Orders-0.0.1-SNAPSHOT.jar graphql-Orders-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/graphql-Orders-0.0.1-SNAPSHOT.jar"]