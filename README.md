# Auction-Service

## Overview

Confluent Kafka를 이용한 프로젝트입니다. 이 프로젝트는 다음과 같은 주요 기능 및 라이브러리를 활용하고 있습니다

- Confluent Kafka
- OpenFeign Client
- Eureka Client for service discovery
- Spring Cloud Config for centralized configuration

## Requirements

- Java 17
- Spring Boot
- Confluent Kafka
- OpenFeign Client

## Stack

<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="java" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="spring" width="40" height="40"/>
  <img src="https://companieslogo.com/img/orig/CFLT-c4a50286.png?t=1627024622" alt="redis" width="40" height="40"/>
</p>

## Mechanism

![auction](https://github.com/wooriFisa-Final-Project-F4/auction-service/assets/119636839/54d4818e-381c-4d23-a8ef-7ed69e29c302)

사용자의 입찰 요청은 api-gateway를 통해 auction-service에 전달된 후 입찰 요청된 상품이 Product-service를 통해 경매가 진행중인 상품인지, Mock API를 통해 입찰 요청 금액을 보유하고 있는지 확인<br>

** Kafka Producer를 통해 이벤트 발행 이벤트의 Key 값을 상품 Id로 설정하여 같은 상품은 같은 파티션에 전달될 수 있도록 설정<br>

- 입찰 요청이 유효하지 않은 경우 입찰 요청 실패 메세지를 반환<br>
- 입찰 요청이 유효한 경우 Kafka 이벤트를 발행합니다.



<br><br>
---
