# PharmacyClaimsProcessor

Welcome to the PharmacyClaimsProcessory Spring Application! The following guide will include instructions for setup via Docker and Kubernetes.

## Versions
Complete details of this project's dependencies can by found in the pom.xml file. Java 21 was used to build this application.

## Docker
### Prerequisites
- [Install Docker](https://docs.docker.com/engine/install/)
- This should include Docker Compose, which is also needed for this projet
- [Install Maven](https://maven.apache.org/install.html)

### Setup
From the root of the project directory, run
```bash
mvn clean package
```
If this doesn't work, try
```bash
mvn clean package -Dskiptest
```
though, this isn't recommended. These commands compile our spring application

Next, run

```bash
docker build -t pharmacyclaimsprocessor:latest .
```
To build the docker image.

Now, we can start our services:
```bash
docker compose up -d
```
This will start
- postgres on port 5432
- kafka (KRaft mode) on port 9092
- pcapp (our application) on port 8080

We can verify that the application is running by visiting http://localhost:8080/actuator/health

Once finished with the environment, run:
```bash
docker compose down -v
```

## Kubernetes
### Prerequisites
- A Kubernetes Cluster (can run locally with Minikube or Kind)
-[kubectl](https://kubernetes.io/docs/tasks/tools/)

### Setup
Build and push the spring Docker image to a registry, like dockerhub:

```bash
docker tag pharmacyclaimsprocessor:latest <dockerhub-username>/pharmacyclaimsprocessor:latest

docker push <dockerhub-username>/pharmacyclaimsprocessor:latest
```
If you are using a local cluster (like via Kind) now would be the time to intialize it:
```bash
kind create cluster
```
From the root of the project directory, you can apply the k8s manifests with this command:
```bash
kubectl apply -f ./k8s
```
Now you should be good to go! Check on the health of the pods with:
```bash
kubectl get pods
```

To test, you can expose the spring boot service locally by port forwarding it:

```bash
kubectl port-forward svc/pcapp-service 8080:8080
```
and then visit http://localhost:8080/actuator/health

Once you're done, clean up k8s as such:
```bash
kubectl delete -f k8s/
```


## Example API Call:
```json
{
  "claimNumber": 123456789,
  "prescriptionNumber": "RX888888",
  "ndc": "12345-6789-00",
  "drugName": "WonderDrug",
  "amountDispensed": 50,
  "fillDate": "2025-07-21",
  "fullTotal": 13.37,
  "coveredTotal": 11.37,
  "patientTotal": 2.0,
  "dateSubmitted": "2025-07-20",
  "status": "SUBMITTED",
  "patient": {
    "patientId": "1",
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "2000-01-01",
    "gender": "MALE",
    "address": "123 Road Street, Kansas City, Kansas 66223",
    "phoneNumber": "555-123-4567",
    "email": "john.doe@example.com"
  },
  "insurance": {
    "npi": "INS123456",
    "insurerName": "Real Health Company",
    "insurerAbv": "RHC",
    "planName": "Full Coverage",
    "groupNumber": "GRP1234",
    "memberId": "MEM56789",
    "coverageStartDate": "2020-01-01",
    "coverageEndDate": "2025-12-31",
    "coinsurancePercent": 20.0,
    "indDeductible": 500.0,
    "famDeductible": 1000.0,
    "maxOutOfPocket": 5000.0,
    "status": "ACTIVE"
  },
  "pharmacy": {
    "name": "Normal Pharmacy",
    "npi": "PHAR123456",
    "contactName": "Alice Pharmacist",
    "phoneNumber": "555-987-6543",
    "email": "pharmacy@example.com",
    "address": "456 Main Street, Kansas City, MO 66221"
  }
}
```
