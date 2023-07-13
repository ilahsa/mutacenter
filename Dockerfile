#base image
FROM  docker.ifeng.com/library/maven:3.5.0-jdk-8-alpine AS build

#maintainer
MAINTAINER wanghuihui@ifeng.com

RUN echo $' \n\
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 \n\
                      https://maven.apache.org/xsd/settings-1.0.0.xsd"> \n\
    <localRepository>/data/ifeng/cache/mvn</localRepository> \n\
    <profiles> \n\
        <profile> \n\
            <id>nexus</id> \n\
            <repositories> \n\
                <repository> \n\
                    <id>central</id> \n\
                    <name>Nexus</name> \n\
                    <url>http://maven.haihang.ifengidc.com:8081/nexus/content/groups/public/</url> \n\
                    <releases><enabled>true</enabled></releases> \n\
                    <snapshots><enabled>true</enabled></snapshots> \n\
                </repository> \n\
                <repository> \n\
                    <id>second</id> \n\
                    <name>Central Repository</name> \n\
                    <url>https://repo.maven.apache.org/maven2</url> \n\
                    <layout>default</layout> \n\
                    <snapshots><enabled>false</enabled></snapshots> \n\
                </repository> \n\
            </repositories> \n\
            <pluginRepositories> \n\
                <pluginRepository> \n\
                    <id>central</id> \n\
                    <name>Nexus</name> \n\
                    <url>http://maven.haihang.ifengidc.com:8081/nexus/content/groups/public/</url> \n\
                    <releases><enabled>true</enabled></releases> \n\
                    <snapshots><enabled>true</enabled></snapshots> \n\
                </pluginRepository> \n\
            </pluginRepositories> \n\
        </profile> \n\
    </profiles> \n\
    <activeProfiles> \n\
        <activeProfile>nexus</activeProfile> \n\
    </activeProfiles> \n\
</settings> '> /usr/share/maven/ref/settings-docker.xml


ADD . /tmp/

RUN mkdir -p /data/ifengsite/htdocs/ \
    && cd /tmp \
    && mvn clean package -DskipTests -gs /usr/share/maven/ref/settings-docker.xml

FROM  docker.ifeng.com/library/openjdk:8u131-jre-alpine

WORKDIR /data/ifengsite/htdocs/

COPY --from=build /tmp/target/  /data/ifengsite/htdocs/

EXPOSE 8888


CMD ["java", \
    "-Xmx2G", \
    "-Xms2G", \
    "-XX:+UseG1GC", \
    "-Duser.timezone=Asia/Shanghai", \
    "-jar", \
    "mutacenter.jar"]