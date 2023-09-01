package f4.domain.kafka;

import f4.domain.dto.request.KafkaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  @Value(value = "${kafka.topic.name}")
  private String topicName;
  private final KafkaTemplate<Long, KafkaDTO> kafkaTemplate;

  public Producer(KafkaTemplate<Long, KafkaDTO> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(KafkaDTO data){
    kafkaTemplate.send(topicName,data.getProductId(),data);
  }
}
