# Auction Service
![image](https://user-images.githubusercontent.com/119636839/267314755-54d4818e-381c-4d23-a8ef-7ed69e29c302.jpg)
> Kafka를 활용한 Auction API를 담당하는 서버입니다.<br> 
> 위 서버는 다른 서비스의 API를 사용하기 위해 Open Feign 통신을 합니다.
<br>


## 🛠️ Dependency
|       기능       | 기술 스택                                                                       |
|:--------------:|:----------------------------------------------------------------------------|
|  Spring Boot   | - Spring Framework 2.7.15<br> - Java 17 <br> - Gradle 8.0 <br> - Spring Web |
|  Spring Cloud  | - Eureka <br> - Config <br> - Gateway <br> - OpenFeign                      |
|Kafka|- Confluent Kafka 7.4.0<br> - Zookeeper 7.4.0

<br>

## 📝 Auction Service 기능

|   기능   | 내용                                                                                                 |
|:------:|:---------------------------------------------------------------------------------------------------|
|  입찰 요청     | 사용자로부터 정보를 입력받아 입찰 요청이 유효한지 검증     |
|  이벤트 발행   | 입찰 요청이 유효한 경우 Kafka Event 발행                 |

<br>

<details>
<summary> 입찰 요청 상세  접기/펼치기</summary>

<h3> Request Feilds </h3>

<table>
    <tr>
        <td> Path </td>
        <td> Type </td>
        <td> Description </td>
    </tr>
    <tr>
        <td> userId </td>
        <td> Long </td>
        <td> 유저 고유 ID (Header) </td>
    </tr>
    <tr>
        <td> productId </td>
        <td> Long </td>
        <td> 상품 고유 ID </td>
    </tr>
    <tr>
        <td> price </td>
        <td> String </td>
        <td> 입찰 요청 금액 </td>
    </tr>
    <tr>
        <td> password </td>
        <td> String </td>
        <td> 결제 패스워드 </td>
    </tr>
</table>
<br>

```
- productId를 사용하여 ProductService에 상품 정보 요청
  상품이 경매 진행 중이 아닌 경우 입찰 요청 실패 반환

- userId, password, price를 사용하여 Mock API에 결제 가능 여부 판단 요청
  유저의 사용 가능 금액이 price보다 낮을 경우 입찰 요청 실패 반환

- 입찰 요청이 유효한 경우 productId를 key값으로 하는 Kafka Event 발행
  (같은 상품은 같은 파티션에 전달될 수 있도록 설정)

```
</details>
<br>

## Auction-Service Prooperties

```properties
#Basic
server.port=[port 번호]
server.servlet.context-path=[base-url]

# EUREKA
eureka.client.service-url.defaultZone=[Eureka-server-ip/eureka]

# KAFKA PRODUCER
spring.kafka.bootstrap-servers=[Kafka Broker ip]
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.LongSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# LOGGING
logging.pattern.console=%green(%d{yyyy-MM-dd HH:mm:ss.SSS}) %magenta([%thread]) %highlight(%-5level) %cyan(%logger{36}) - %yellow(%msg%n)
logging.level.org.hibernate.SQL=debug
logging.file.path=logs

#Values
kafka.topic.name=[produce topic name]
mock.url=[mock api url]
```


