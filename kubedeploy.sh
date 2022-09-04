#!/bin/bash
#------------------------------------------------------------------------------------------
# kubernate 배포 스크립트 Created By 이병관
#------------------------------------------------------------------------------------------
#-- ECR정보
ECR=104844728320.dkr.ecr.ap-northeast-2.amazonaws.com
ver=latest                       #-- 기본버전은 v1
cmd=apply

case $# in
  1) cmd=$1 ;;
  2) cmd=$1 ; ver=$2;;
esac


cat << EOF | kubectl $cmd -f -
apiVersion: apps/v1
kind: Deployment
metadata:
  name: member
  namespace: ecomarket
  labels:
    app: member
spec:
  replicas: 1
  selector:
    matchLabels:
      app: member
  template:
    metadata:
      labels:
        app: member
    spec:
      containers:
        - name: member
          image: $ECR/member:$ver
          imagePullPolicy: Always
          ports:
            - containerPort: 8081
      tolerations:
        - key: "node.kubernetes.io/unreachable"
          operator: "Exists"
          effect: "NoExecute"
          tolerationSeconds: 6000
---
apiVersion: v1
kind: Service
metadata:
  name: member-svc
  namespace: ecomarket
  labels:
    app: member
spec:
  ports:
    - port: 80
      targetPort: 8081
  selector:
    app: member
  type: NodePort
EOF
