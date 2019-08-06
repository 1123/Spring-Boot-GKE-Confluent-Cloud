### Overview

This project shows how to connect a Spring Boot application to Apache Kafka on Confluent Cloud, and how to deploy the application on Google Kubernetes Engine. 

The Java Code is taken from Gary Russel's excellent blog post on Spring Kafka here: 
https://www.confluen.io/blog/spring-for-apache-kafka-deep-dive-part-1-error-handling-message-conversion-transaction-support

### Prerequisites

* Basic familiarity with Kafka, Java, Spring, Google Cloud Platform, Kubernetes
* Registration with Confluent Cloud
* An API key and secret for Confluent Cloud
* A Google Clout Platform Project
* A Kubernetes cluster in your GCP project
* `glcoud` cli installed and targeted at your GCP project
* `kubectl` cli installed and pointed at your Kubernetes cluster

### Running the demo

* Copy `configmap.yaml.template` to `configmap.yaml` and insert your API key and secret
* Copy `cloudbuild.yaml.template` to `cloudbuild.yaml` and insert your GCP project identifier 
* Build this project using Google Cloud Build: `./build.sh`. 
  This will build the Spring Boot App and a docker image and register your image in Google Container Registry. 
* Copy `deployment.yaml.template` to `deployment.yaml` and insert the link to your newly built image. 
* Deploy the config map: `kubectl create -f configmap.yaml`
* Deploy the application: `kubectl create -f deployment.yaml`
* Deploy a kubernetes service for external access: `kubectl create -f service.yaml`
* Use the GKE web interface or kubectl cli to find the external IP of your service: ` kubectl get service confluent-cloud-spring-kafka-sample-service`
* Post a message to your app: `curl -X POST http://<external-ip>:8080/send/foo/this-is-a-message`
* Verify in Confluent Cloud that the message has arrived. 

