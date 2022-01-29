# Stock app

Stock web application - first project to my CV - still a lot to add or improve - Technologies: Spring, JPA, Hibernate

This app was created to simulate transactions you can do on Warsaw Stock Exchange with real time listings.

# Prerequisites:

* Java (this project is using version 11)
* Docker
* Maven

If you would like to run this application in kubernetes cluster, you may also need:
* Minikube
* Kubectl

# Running the app:

###Docker:

After pulling repository from GitHub just enter the directory where the code is and enter the commands:

``
maven package
``

``
docker compose up
``

and everything will be done for you automatically.

###Kubernetes:

After setting up minikube, build the docker image (don't forget to be in a proper directory) using:

``
maven package (if you haven't done this already)
``

``
docker build -t stock-app:v1 .
``

When image is done and dusted we can move to the next step, which is setting up K8S components:

``
kubectl apply -f mysql-configmap.yaml
``

``
kubectl apply -f mysql-secret.yaml
``

``
kubectl apply -f stock-app-configmap.yaml
``

Now we can finally create our pods:

``
kubectl apply -f mysql.yaml
``

``
kubectl apply -f stock-app.yaml
``

To head to the page where service is running type:

``
minikube service stock-app-service
``

# Endpoints:

To learn about available endpoints head to:

*{host}/swagger-ui/index.html*
