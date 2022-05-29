# REQ-QA: Automatic identification of possible deficiencies in the introduction of requirements
## Introduction

This project was created as a part of the end degree project ***Automatic identification of possible deficiencies in the introduction of requirements***    
in collaboration with [Facultat d'Informàtica de Barcelona](https://www.fib.upc.edu/ca).

The service analyze the requirements in natural language and return a deficiencies found. 

The service has the aim to allow the extensibility as one of the pillars in order to add more functionality.

### Used OpenReq component:
- [Improving Requirements Quality](https://github.com/OpenReqEU/prs-improving-requirements-quality)

***
## Technical description

REQ-QA is based on Java Spring Boot and use Maven.

### How to extend with external service
Create a maven module and implements ***IRequirementExternalService*** interface extending to ***RequirementExternalService***.

The core engine will call all the services that implements the interface and aggregates the results.

### Previous requisites
- Install Docker (-> https://www.docker.com/)
- OpenReq - Improving Requirements Quality docker running, see [installation instructions](https://github.com/OpenReqEU/prs-improving-requirements-quality#how-to-install).

#### Build the project 
```
mvn clean install -DSkipTest
```

#### Create a Docker image
```
docker build -t reqqa .
```
#### Run the docker image
```
docker run -p 8089:8089 -e "JAVA_OPTS=-Dopenreq.baseUrl=http://host.docker.internal:9799" reqqa
```

## How to use

There are a core API to use (/analyze), see [swagger documentation](http://localhost:8089/swagger-ui.html#/)

There is a [form web page](http://localhost:8089/) to use as well.


## Taiga integration
In order to operate with Taiga, there are a previous configuration that has to be done:
- [Install Taiga local with docker](https://docs.taiga.io/setup-production.html#setup-prod-with-docker) or use an existing Taiga server.
- Create a custom field named 'quality' by default as a Rich text.
- Configure a WebHook (Integrations->Webhook) with the url: http://host.docker.internal:8089/taiga/webhook and a secret.

### Run the docker image with Taiga integration
When run the docker image, send the configured parameters as:
- **taiga.baseUrl**: the url of Taiga server
- **taiga.secret**: secret key configured in Taiga for the webhook
- **taiga.customFieldName**: custom field name, by default "quality"
- **taiga.username**: username in Taiga used to modify the issues
- **taiga.password**: password for the user in Taiga
```
docker run -p 8089:8089 -e "JAVA_OPTS=-Dopenreq.baseUrl=http://host.docker.internal:9799 -Dtaiga.baseUrl=http://host.docker.internal:9000 -Dtaiga.customFieldName=mycustomfield -Dtaiga.username=username -Dtaiga.password=password" reqqa
```
