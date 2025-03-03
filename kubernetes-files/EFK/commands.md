## Deploy Elasticsearch
```bash
kubectl apply -f elasticsearch-deployment.yml
kubectl apply -f elasticsearch-service.yml
```
## Deploy Kibana:
```bash
kubectl apply -f kibana-deployment.yml
kubectl apply -f kibana-service.yml
```
## Deploy Fluentd
```bash
kubectl apply -f fluentd-configmap.yml
kubectl apply -f fluentd-daemonset.yml
```
## Visualize Logs in Kibana
```bash
kubectl port-forward svc/kibana 5601:5601
```


Open http://localhost:5601 in your browser

#### Set Up Index Patterns:
Go to Stack Management > Index Patterns.

Create an index pattern (e.g., logstash-*).
```bash
kubectl exec -it elasticsearch-5cb5ccdb47-xfl9 -- bin/elasticsearch-create-enrollment-token -s kibana
```
