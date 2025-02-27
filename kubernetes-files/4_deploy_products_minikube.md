## Verify Minikube is running
Open terminal and run the following command to verify that Minikube is running:
```bash
minikube status
```

## Apply Secrets
Secrets should be applied first since they are referenced by the deployments.
```bash
kubectl apply -f 3_products-secret.yaml
```
You can verify that the secret was created by running the following command:
```bash
kubectl get secrets
```

## Apply ConfigMap
```bash
kubectl apply -f 3_products-config.yaml
```
Verify with:
```bash
kubectl get configmaps
```

## Apply Deployment
```bash
kubectl apply -f 3_products-deployment.yaml
```
Verify with:
```bash
kubectl get deployments
```
Verify pods are running:
```bash
kubectl get pods
```

## Apply Services
```bash
kubectl apply -f 3_products-service.yaml
```
Verify with:
```bash
kubectl get services
```

Namespaces: If you're using namespaces, make sure to apply resources to the correct namespace using the `-n <namespace_name>` option.

Updates: If you need to update a resource, you can re-run `kubectl apply -f <file_name.yaml>`. Kubernetes will apply the changes.

Deleting: To delete a resource, use `kubectl delete -f <file_name.yaml>` or `kubectl delete <resource_type> <resource_name>`.


