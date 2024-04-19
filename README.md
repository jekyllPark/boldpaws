# 프로젝트 구성도 (임시)
![당당하개 drawio](https://github.com/jekyllPark/boldpaws/assets/114489012/c5480a3a-8588-4eaa-988f-d4df57d077d8)

# 서드파티
- 인증 / 인가
  - OAuth2 (현재 구글만)
  - JWT
  - Spring API Gateway 도입 예정
- 모니터링
  - ELK (로그)
  - Prometheus + Grafana (메트릭)
- 테스트
  - JaCoCo (커버리지)
  - REST Doc (테스트 문서화)
- 명세
  - OpenAPI (Swagger)
- 암호화
  - Jasypt 예정

# 도커 실행 방법
```
cd docker-compose
docker-compose up -d
```

## Prometheus + Grafana


![image](https://github.com/jekyllPark/boldpaws/assets/114489012/4601fa19-e115-4ed4-8c5a-65f182cb6ccf)


## ELK


![image](https://github.com/jekyllPark/boldpaws/assets/114489012/530b1b4b-071e-4aa4-8e38-11194e149d02)
