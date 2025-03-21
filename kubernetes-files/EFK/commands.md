kubectl create namespace kube-logging
kubectl apply -f elasticsearch-deployment.yml
kubectl apply -f elasticsearch-service.yml
kubectl apply -f fluentd-sa.yaml
kubectl apply -f fluentd-clusterrole.yaml
kubectl apply -f fluentd-clusterrolebiding.yaml
kubectl apply -f fluentbit-configmap.yaml
kubectl apply -f fluentbit-daemonset.yaml
kubectl apply -f fluentd-deployment.yaml
kubectl apply -f fluentd-service.yaml
kubectl apply -f kibana-deployments.yaml
kubectl apply -f kibana-service.yaml


kubectl get pods -n kube-logging

kubectl port-forward svc/kibana -n kube-logging 5601:5601
