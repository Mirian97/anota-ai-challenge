package com.quispe.anota_ai_challenge.services.aws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwsSnsService {
    private final AmazonSNS snsClient;
    @Qualifier("catalogEventsTopic")
    private final Topic catalogTopic;
    
    public void publish(MessageDTO message){
        this.snsClient.publish(catalogTopic.getTopicArn(), message.message());
    }
}
