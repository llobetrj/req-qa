


To build 
```
mvn install -DSkipTest
```

Docker
```
docker build -t reqqa .
docker run -p 8089:8089 -e "JAVA_OPTS=-Dopenreq.baseUrl=http://host.docker.internal:9799 -Dtaiga.baseUrl=http://host.docker.internal:9000" reqqa
```

