This project shows how to connect a Spring Boot application to Apache Kafka on Confluent Cloud, and how to deploy the application on Google Kubernetes Engine. 

The Java Code is taken from Gary Russel's excellent blog post on Spring Kafka here: 
https://www.confluen.io/blog/spring-for-apache-kafka-deep-dive-part-1-error-handling-message-conversion-transaction-support

How to run this demo: 

* Register with Confluent Cloud and create an API key 
* Copy configmap.yaml.template to configmapl.yaml and insert your API key and secret
* Create a Google Compute Engine project
* Install the gcloud command line interface 
* Copy cloudbuild.yaml.template to cloudbuild.yaml and insert your project identifier 
* Build this project using Google Cloud Build: `./build.sh`. This will register your image in Google Container Registry. 
* Create a Kubernetes cluster in your Google Compute Engine project
* Configure kubectl to point to your kubernetes cluster
* Copy deployment.yaml.template to deployment.yaml and insert the link to your newly built image. 
* Deploy the config map: `kubectl create -f configmap.yaml`
* Deploy the applicationi: `kubectl create -f deployment.yaml`
* Deploy a kubernetes service for external access: `kubectl create -f service.yaml`
* Use the GKE web interface or kubectl cli to find the external IP of your service
* Post a message to your app: curl http://<external-ip>/send/foo/this-is-a-message
* Verify in Confluent Cloud that the message has arrived. 

