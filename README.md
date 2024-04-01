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

![image](https://github.com/jekyllPark/boldpaws/assets/114489012/4601fa19-e115-4ed4-8c5a-65f182cb6ccf)


## ELK

```
-- 디렉토리 변경
cd monitor/log
-- 데몬으로 도커라이징
docker-compose up -d

1. es -> 9200 포트
2. kibana -> 5601 포트
```

![image](https://github.com/jekyllPark/boldpaws/assets/114489012/530b1b4b-071e-4aa4-8e38-11194e149d02)
