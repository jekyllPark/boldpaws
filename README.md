# 도커 실행 방법

## Prometheus + Grafana

```
-- 디렉토리 변경
cd monitor/metric
-- 데몬으로 도커라이징
docker-compose up -d

1. prometheus -> 9090 포트
2. grafana -> 3000 포트
```

## ELK

```
-- 디렉토리 변경
cd monitor/log
-- 데몬으로 도커라이징
docker-compose up -d

1. es -> 9200 포트
2. kibana -> 5601 포트
```