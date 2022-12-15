FROM openjdk:11

ENV SBT_VERSION 1.8.0
RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip
RUN unzip sbt-$SBT_VERSION.zip -d ops

WORKDIR /Marczyk_Scala_BI
ADD . /Marczyk_Scala_BI

RUN /ops/sbt/bin/sbt assembly

CMD ["java", "-jar", "/Marczyk_Scala_BI/target/scala-2.12/Marczyk_Scala_BI-assembly-0.1.0-SNAPSHOT.jar"]