package f4.domain.kafka;

import f4.domain.dto.KafkaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  @Value(value = "${kafka.topic.name}")
  private String topicName;
  private final KafkaTemplate<Long, KafkaDto> kafkaTemplate;

  public Producer(KafkaTemplate<Long, KafkaDto> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(KafkaDto kafkaDTO) {
    kafkaTemplate.send(topicName, kafkaDTO.getProductId(), kafkaDTO);
  }
}
