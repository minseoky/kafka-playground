# Kafka Proof of Concept (PoC) 프로젝트

## 1. 프로젝트 개요

이 레포지토리는 Apache Kafka의 다양한 기능을 학습하고 실험하기 위한 PoC(Proof of Concept) 코드 모음입니다.

하나의 완성된 애플리케이션이 아닌, 다양한 Kafka 사용 사례, 통합 패턴, 운영 특징을 테스트하기 위한 샌드박스 환경입니다. 여러가지 실험을 통해 Kafka에 대한 이해를 높이는 것을 목표로 합니다.

## 2. 주요 특징 및 학습 목표

이 프로젝트는 다음과 같은 Kafka의 핵심 기능과 통합 패턴을 실험하기 위해 구성되었습니다.

-   **Kafka 클러스터 운영**: 3개의 노드로 구성된 Kafka 클러스터를 Docker Compose로 실행하고 관리합니다.
-   **데이터 직렬화 (Serialization)**: Avro와 Confluent Schema Registry를 연동하여 메시지 스키마를 체계적으로 관리하고 데이터 호환성을 보장합니다.
-   **Producer/Consumer 구현**: Spring Kafka를 사용하여 메시지를 생산하고 소비하는 기본적인 로직을 구현합니다.
-   **통합 아키텍처**: Kafka를 중심으로 MySQL, Redis 등 다른 데이터 저장소와 연동하는 패턴을 실험합니다.
-   **모니터링**: Kafka UI를 통해 클러스터와 메시지를 시각적으로 확인합니다.

## 3. 기술 스택

-   **Language**: `Java 17`
-   **Framework**: `Spring Boot`
-   **Build Tool**: `Gradle`
-   **Messaging**: `Apache Kafka`
-   **Serialization**: `Avro`, `Confluent Schema Registry`
-   **Database**: `MySQL`
-   **In-memory Store**: `Redis`
-   **Environment**: `Docker`, `Docker Compose`

## 4. 시스템 아키텍처

전체 시스템은 `docker-compose`를 통해 컨테이너 환경에서 실행됩니다.

-   `app`: Spring Boot 애플리케이션
-   `kafka1`, `kafka2`, `kafka3`: 3-node Kafka 클러스터
-   `schema-registry`: Avro 스키마 관리를 위한 Schema Registry
-   `kafka-ui`: Kafka 관리를 위한 웹 UI
-   `mysql`: 데이터 영속성을 위한 RDBMS
-   `redis`: 캐시 또는 임시 데이터 저장을 위한 인메모리 저장소

## 5. 시작하기

### 요구사항

-   `Docker` & `Docker Compose`
-   `Java 17`

### 실행 순서

1.  **Docker Compose로 전체 인프라 실행**

    `deploy` 디렉토리의 `docker-compose.yaml` 파일을 사용하여 Kafka, Schema Registry, MySQL 등 모든 서비스를 실행합니다.

    ```bash
    docker-compose -f deploy/docker-compose.yaml up -d
    ```

2.  **서비스 확인**

    -   **Kafka UI**: 브라우저에서 `http://localhost:8088` 로 접속하여 Kafka 클러스터 상태를 확인합니다.

3.  **Spring Boot 애플리케이션 실행**

    Gradle을 사용하여 Spring Boot 애플리케이션을 실행합니다.

    ```bash
    ./gradlew bootRun
    ```

## 6. 향후 실험 계획

이 프로젝트를 기반으로 다음과 같은 주제들을 추가적으로 실험해볼 예정입니다.

-   [ ] **Kafka Streams / ksqlDB**: 스트림 처리 및 실시간 데이터 분석
-   [ ] **트랜잭션 처리**: Exactly-Once Semantics (EOS) 구현
-   [ ] **오류 처리**: Dead Letter Queue (DLQ) 패턴 적용
-   [ ] **성능 테스트**: 대용량 메시지 처리 시나리오 테스트
-   [ ] **보안**: SASL/SSL을 이용한 클라이언트 인증 및 데이터 암호화
-   [ ] **Consumer Group**: Rebalancing 전략 및 Lag 모니터링
