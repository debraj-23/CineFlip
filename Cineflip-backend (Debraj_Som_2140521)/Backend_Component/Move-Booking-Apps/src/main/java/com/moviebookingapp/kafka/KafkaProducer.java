package com.moviebookingapp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;



@Service
public class KafkaProducer {
	private static final Logger LOGGER=LoggerFactory.getLogger(KafkaProducer.class);
	@Autowired
	private KafkaTemplate<Integer, String>kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, String>kafkaTemplateForStatusUpdation;
	public void sendMessage(Integer noOfTicketsBookedForAMove) {
		//LOGGER.info(String.format("Number of tickets for a movie ->%d",noOfTicketsBookedForAMove));
		Message<Integer>message=MessageBuilder.withPayload(noOfTicketsBookedForAMove)
				.setHeader(KafkaHeaders.TOPIC,"movietickets").build();
		kafkaTemplate.send(message);
   
}
	public void sendMessage(String updatedStatus) {
		//LOGGER.info(String.format("Number of tickets for a movie ->%d", updatedStatus));
		System.out.println("Hi");
		Message<String>message=MessageBuilder.withPayload(updatedStatus)
				.setHeader(KafkaHeaders.TOPIC,"movietickets").build();
		kafkaTemplate.send(message);
   
}
}