FROM ubuntu

RUN apt update && apt-get upgrade -y
RUN apt install curl -y
RUN apt install git -y
RUN apt install bash -y
RUN apt install zip -y
RUN apt install maven -y


RUN curl -s "https://get.sdkman.io" | bash
RUN echo 'PATH=$PATH:$HOME/.jenv/bin:$HOME/.sdkman/bin' >> /etc/environment
RUN echo '. $HOME/.sdkman/bin/sdkman-init.sh' >> /etc/environment


RUN bash -c '. /etc/environment && sdk install java 17.0.7-amzn'

RUN apt-get install language-pack-ru -y
RUN echo 'LANG="ru_RU.UTF-8"\nLANGUAGE="ru:en"' >> /etc/default/locale
ENV LANGUAGE ru_RU.UTF-8
ENV LANG ru_RU.UTF-8
ENV LC_ALL ru_RU.UTF-8
RUN locale-gen ru_RU.UTF-8 && dpkg-reconfigure locales
RUN bash -c '. /etc/environment && sdk use java 17.0.7-amzn && sdk install gradle'

ENV TOMCAT_VERSION 10.1.17
WORKDIR /opt
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://dlcdn.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar -xzvf apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    mv apache-tomcat-${TOMCAT_VERSION} tomcat && \
    rm apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    apt-get remove -y wget && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Установите порт, на котором Tomcat будет слушать
EXPOSE 8080


WORKDIR /tests
VOLUME /tests
ENV PATH=$PATH:/tests/postgresql-42.6.0.jar
COPY ./test.sh /

RUN mkdir -p /usr/local/tomcat/lib
#ENTRYPOINT bash -c 'bash test.sh'
#ENTRYPOINT bash -c '. /etc/environment && sdk use java 17.0.7-amzn && gradle build && java -classpath /tests/libs/postgresql-42.6.0.jar:/tests/build/libs/consoleApp-1.0-SNAPSHOT.jar org.example.Main'
ENTRYPOINT bash -c 'rm -fr build/ &&. /etc/environment && sdk use java 17.0.7-amzn && gradle build && cp build/libs/*.war /opt/tomcat/webapps/api.war && cd /tests/build/libs/ && unzip *.war && cp /tests/build/libs/WEB-INF/lib/*.jar /opt/tomcat/lib/ && cp /tests/build/libs/WEB-INF/lib/*.jar /usr/local/tomcat/lib && /opt/tomcat/bin/catalina.sh run'

#CMD ping 127.0.0.1

#sudo docker pull alpine
#sudo docker build -t test .
#docker run -p 32999:32999 --rm -v ./:/tests -it test sh